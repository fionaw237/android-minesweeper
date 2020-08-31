package com.example.android_minesweeper.screens.best_times

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_minesweeper.models.HighScoreDao
import com.example.android_minesweeper.view_models.HighScoresViewModel

class HighScoresViewModelFactory(private val highScoreDao: HighScoreDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighScoresViewModel::class.java)) {
            return HighScoresViewModel(highScoreDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}