package com.example.android_minesweeper.view_models

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.models.HighScoreDao
import com.example.android_minesweeper.models.HighScoresRepository
import kotlinx.coroutines.*

class HighScoresViewModel(highScoreDao: HighScoreDao) : BaseViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val highScoresRepository = HighScoresRepository(highScoreDao)

    @Bindable
    var defaultDifficulty = Difficulty.BEGINNER

    @Bindable
    var highScores: MutableMap<Difficulty, List<HighScore>> = mutableMapOf()

    fun getBestTimes() {
        uiScope.launch {
            Difficulty.values().forEach { difficulty ->
                highScores[difficulty] = highScoresRepository.getScoresFromDatabase(difficulty)
            }
            notifyPropertyChanged(BR.highScores)
        }
    }

    fun deleteBestTimesButtonPressed() {
        uiScope.launch {
            highScoresRepository.deleteScores()
            highScores = mutableMapOf()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun difficultyForAdapterPosition(position: Int) =
        when (position) {
            0 -> Difficulty.BEGINNER
            1 -> Difficulty.INTERMEDIATE
            else -> Difficulty.ADVANCED
        }
}