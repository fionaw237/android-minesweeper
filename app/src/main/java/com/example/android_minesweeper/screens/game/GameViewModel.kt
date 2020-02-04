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

    var timerStarted = false

    @Bindable
    var flagsRemaining: String = ""

    @Bindable
    var gridCells: MutableList<GridCell> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.gridCells)
        }

    init {
        setUpGame()
    }

    private fun setUpGame() {
        timerStarted = false
        setNumberOfMinesFromDifficulty()
        setUpGridCells()
    }

    private fun setNumberOfMinesFromDifficulty() {
        numberOfMines = when (difficulty) {
            Difficulty.BEGINNER -> 5
            Difficulty.INTERMEDIATE -> 8
            Difficulty.ADVANCED -> 12
        }
    }

    private fun randomMinePositions(): Set<Int> {
        var positions = mutableSetOf<Int>()
        while (positions.size < numberOfMines) {
            (1..(cellsPerRow * numberOfRows)).random().also { randomPosition ->
                if (!positions.contains(randomPosition)) {
                    positions.add(randomPosition)
                }
            }
        }
        return positions
    }

    private fun randomlyDistributeMines() {
        //TODO: should never click on a mine with the first click!
        randomMinePositions().also { positions ->
            gridCells.forEach { cell ->
                cell.hasMine = positions.contains(cell.positionInGrid)
            }
        }
    }

    private fun setUpGridCells() {
        gridCells = (1..(cellsPerRow * numberOfRows)).map { position ->
            GridCell(positionInGrid = position)
        }.toMutableList()
    }

    fun gridCellTapped(gridCell: GridCell) {
        if (gridCell.uncovered || gridCell.hasFlag) return

        if (!timerStarted) {
            randomlyDistributeMines()
            timerStarted = true
        }

        gridCell.uncovered = true

        if (gridCell.hasMine) {
            gameOver(clickedCell = gridCell)
            return
        }

        gridCell.minesInVicinity = numberOfMinesInVicinityOfCell(gridCell)

        if (gridCell.minesInVicinity == "0") {
            revealSurroundingCellsWithZeroMines(gridCell)
        }

        refreshGridCells(gridCells)
    }

    private fun numberOfMinesInVicinityOfCell(cell: GridCell): String {
        return "2"
    }

    private fun revealSurroundingCellsWithZeroMines(cell: GridCell) {

    }

    private fun refreshGridCells(updatedGridCells: MutableList<GridCell>) {
        gridCells = updatedGridCells
    }

    private fun gameOver(clickedCell: GridCell) {
        clickedCell.cellClickedForGameOver = true
        configureCellsForGameOver()
        // invalidate timer
        // play sound
        // configure reset button
        refreshGridCells(gridCells)
    }

    fun resetGrid() {
        setUpGame()
    }

    private fun configureCellsForGameOver() {
        gridCells.forEach { cell ->
            cell.configureForGameOver()
        }
    }

    private fun setFlagsLabel() {
        if (flagsRemaining.toInt() != 0) {
            flagsRemaining = (flagsRemaining.toInt() - 1).toString()
        }
    }
}