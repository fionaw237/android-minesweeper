package com.example.android_minesweeper.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.models.HighScoreDao
import com.example.android_minesweeper.view_models.GameViewModel

class GameViewModelFactory(private val difficulty: Difficulty, private val highScoreDao: HighScoreDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(difficulty, highScoreDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}