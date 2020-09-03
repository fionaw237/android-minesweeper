package com.example.android_minesweeper.models

import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.getDifficultyEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HighScoresRepository(private val highScoreDao: HighScoreDao) {

    suspend fun getScoresFromDatabase(difficulty: Difficulty): List<HighScore> {
        return withContext(Dispatchers.IO) {
            highScoreDao.getByDifficulty(difficulty = difficulty.value).sortedBy { it.time }
        }
    }

    fun storeNewHighScore(highScore: HighScore) {
        GlobalScope.launch {
            getScoresFromDatabase(highScore.difficulty.getDifficultyEnum()!!).also { scores ->
                if (scores.count() >= 10) {
                    // Update the lowest score with the new values
                    val scoreToUpdate = scores.last()
                    scoreToUpdate.name = highScore.name
                    scoreToUpdate.time = highScore.time
                    highScoreDao.insert(scoreToUpdate)
                } else {
                    // No low score exists - create new entry
                    highScoreDao.insert(highScore)
                }
            }
        }
    }

    private suspend fun clearScores() {
        withContext(Dispatchers.IO) {
            highScoreDao.clear()
        }
    }

    suspend fun isNewBestTime(time: Long, difficulty: Difficulty): Boolean {
        getScoresFromDatabase(difficulty).also { scores ->
            if (scores.count() >= 10 && scores.last().time < time) {
                return false
            }
        }
        return true
    }
}