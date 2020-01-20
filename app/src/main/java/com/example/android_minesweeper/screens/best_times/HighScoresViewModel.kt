package com.example.android_minesweeper.screens.best_times

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.database.HighScore
import com.example.android_minesweeper.database.HighScoreDao
import com.example.android_minesweeper.screens.BaseViewModel
import kotlinx.coroutines.*

class HighScoresViewModel(private val highScoreDao: HighScoreDao) : BaseViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    @Bindable
    var highScores: List<HighScore> = listOf()
    set(value) {
        field = value
        notifyPropertyChanged(BR.highScores)
    }

    // TODO: Later, store high scores in a map, once the fragment has loaded -
    //  then when a difficulty is chosen from the dropdown, just grab the relevant data from the map

    fun difficultyChosenToDisplay(difficulty: Difficulty) {
//        uiScope.launch {
//            highScores = getScoresFromDatabase(difficulty)
//        }
        highScores = listOf(
            HighScore(1, "Beginner", "Eva", "20s"),
            HighScore(2, "Beginner", "Eva", "22s"),
            HighScore(3, "Beginner", "Eva", "24s"),
            HighScore(4, "Beginner", "Eva", "26s")
        )
    }

    private suspend fun getScoresFromDatabase(difficulty: Difficulty): List<HighScore> {
        return withContext(Dispatchers.IO) {
            highScoreDao.getByDifficulty(difficulty = difficulty.value)
        }
    }

    fun clearButtonPressed() {
        uiScope.launch {
            clearScores()
        }
    }

    private suspend fun clearScores() {
        withContext(Dispatchers.IO) {
            highScoreDao.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}