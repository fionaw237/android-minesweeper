package com.example.android_minesweeper.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_minesweeper.Difficulty

class GameViewModelFactory(private val difficulty: Difficulty) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(difficulty) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}