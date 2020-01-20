package com.example.android_minesweeper.screens.game

import android.util.Log
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.screens.BaseViewModel

class GameViewModel(private val difficulty: Difficulty) : BaseViewModel() {

    var numberOfMines: Int = 0
    val numberOfColumns = 6
    val cellsPerRow = 6

    init {
       setNumberOfMinesFromDifficulty()
        Log.d("number of mines", numberOfMines.toString())
    }

    private fun setNumberOfMinesFromDifficulty() {
        numberOfMines = when (difficulty) {
            Difficulty.BEGINNER -> 5
            Difficulty.INTERMEDIATE -> 8
            Difficulty.ADVANCED -> 12
        }
    }
}