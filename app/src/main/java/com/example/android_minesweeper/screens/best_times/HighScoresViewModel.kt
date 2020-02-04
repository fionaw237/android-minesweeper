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

    //        listOf(
//            HighScore(difficulty = "Beginner", name = "Eva", time = "20s")
//        ).forEach {
//            uiScope.launch {
//                addHighScoreToDatabase(it)
//            }
//        }.also {
//            uiScope.launch {
//                highScores = getScoresFromDatabase(Difficulty.BEGINNER)
//            }
//        }

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

    private suspend fun addHighScoreToDatabase(highScore: HighScore) {
        withContext(Dispatchers.IO) {
            highScoreDao.insert(highScore)
        }
    }

    fun clearButtonPressed() {
        uiScope.launch {
            clearScores()
            //TODO: Add repository layer which the view model can observe?
            highScores = listOf()
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