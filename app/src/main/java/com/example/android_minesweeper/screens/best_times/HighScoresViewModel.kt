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
        uiScope.launch {
            highScores = getScoresFromDatabase(difficulty)
        }
            //TODO: we shouldnt be assigning an ID when initialising the object - need to write a function to assign a unique ID
        // between 1 and 10, say (if we're just storing 10 scores).
        // 1) Determine if high score based on other existing scores
        // 2) If it is a new high score, assign appropriate ID
//        listOf(
//            HighScore(1100, "Intermediate", "Eva", "20s")
//        ).forEach {
//            uiScope.launch {
//                addHighScoreToDatabase(it)
//            }
//        }.also {
//            uiScope.launch {
//                highScores = getScoresFromDatabase(Difficulty.BEGINNER)
//            }
//        }

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