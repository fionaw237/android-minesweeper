package com.example.android_minesweeper.screens.best_times

import androidx.lifecycle.ViewModel
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.database.HighScore
import com.example.android_minesweeper.database.HighScoreDao
import kotlinx.coroutines.*

class HighScoresViewModel(private val highScoreDao: HighScoreDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var highScores: List<HighScore> = listOf()

    // TODO: Later, store high scores in a map, once the fragment has loaded -
    //  then when a difficulty is chosen from the dropdown, just grab the relevant data from the map

    fun difficultyChosenToDisplay(difficulty: Difficulty) {
        uiScope.launch {
            highScores = getScoresFromDatabase(difficulty)
        }
    }

    private suspend fun getScoresFromDatabase(difficulty: Difficulty): List<HighScore> {
        return withContext(Dispatchers.IO) {
            highScoreDao.getByDifficulty(difficulty = difficulty.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}