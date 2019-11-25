package com.example.android_minesweeper.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel(difficulty: Int) : ViewModel() {

    init {
        Log.d("Difficulty", difficulty.toString())
    }
}