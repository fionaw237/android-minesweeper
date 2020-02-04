package com.example.android_minesweeper.screens.game

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.screens.BaseViewModel

class GameViewModel(private val difficulty: Difficulty) : BaseViewModel() {

    var numberOfMines: Int = 0
        set(value) {
            field = value
            flagsRemaining = value.toString()
        }

    private val numberOfRows = 8
    val cellsPerRow = 8

    @Bindable
    var flagsRemaining: String = ""

    @Bindable
    var gridCells: MutableList<GridCell> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.gridCells)
        }

    init {
       setNumberOfMinesFromDifficulty()
        gridCells = (1..(cellsPerRow * numberOfRows)).map {
            GridCell(hasMine = (it % 2 == 0))
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
        if (!gridCell.uncovered) {
            gridCells.find { it == gridCell }?.let {
                it.uncovered = true
            }
            gridCells = gridCells
        }
    }

    fun resetGrid() {
        gridCells = (1..(cellsPerRow * numberOfRows)).map {
            GridCell(hasMine = (it % 2 == 0))
        }.toMutableList()
    }
}