package com.example.android_minesweeper.screens.game

import androidx.lifecycle.MutableLiveData
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.screens.BaseViewModel

class GameViewModel(private val difficulty: Difficulty) : BaseViewModel() {

    var numberOfMines: Int = 0
    private val numberOfRows = 8
    val cellsPerRow = 8

    var gridCells: MutableLiveData<MutableList<GridCell>> = MutableLiveData()

    init {
       setNumberOfMinesFromDifficulty()
        gridCells.value = (1..(cellsPerRow * numberOfRows)).map {
            GridCell( )
        }.toMutableList()
    }

    private fun setNumberOfMinesFromDifficulty() {
        numberOfMines = when (difficulty) {
            Difficulty.BEGINNER -> 5
            Difficulty.INTERMEDIATE -> 8
            Difficulty.ADVANCED -> 12
        }
    }

    fun gridCellTapped(gridCell: GridCell) {
//        if (!gridCell.uncovered) {
//            val listCopy = gridCells.value?.toMutableList()
//            listCopy?.find { it == gridCell }?.let {
//                it.uncovered = true
//            }
//            gridCells.value = listCopy
//        }
    }
}