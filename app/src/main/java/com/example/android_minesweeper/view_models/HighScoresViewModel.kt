package com.example.android_minesweeper.view_models

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.getDifficultyEnum
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.models.HighScoreDao
import com.example.android_minesweeper.models.HighScoresRepository
import kotlinx.coroutines.*

class HighScoresViewModel(private val highScoreDao: HighScoreDao) : BaseViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val highScoresRepository = HighScoresRepository(highScoreDao)

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
            highScores = highScoresRepository.getScoresFromDatabase(difficulty)
        }
    }

//    fun clearButtonPressed() {
//        uiScope.launch {
//            clearScores()
//            //TODO: Add repository layer which the view model can observe?
//            highScores = listOf()
//        }
//    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}