package com.example.android_minesweeper.screens.game

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.models.GridCell

class GameViewModel(private val difficulty: Difficulty) : ViewModel() {

    var numberOfMines: Int = 0
    val numberOfColumns = 6
    val cellsPerRow = 6

    var gridCells: MutableLiveData<MutableList<GridCell>> = MutableLiveData()

    init {
       setNumberOfMinesFromDifficulty()
        Log.d("number of mines", numberOfMines.toString())
        val list = mutableListOf<GridCell>()
        (1..numberOfMines).forEach {
            list.add(GridCell())
        }
        gridCells.value = list
    }

    private fun setNumberOfMinesFromDifficulty() {
        numberOfMines = when (difficulty) {
            Difficulty.BEGINNER -> 5
            Difficulty.INTERMEDIATE -> 8
            Difficulty.ADVANCED -> 12
        }


    }
}