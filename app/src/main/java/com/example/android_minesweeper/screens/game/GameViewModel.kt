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

    private fun randomMinePositions(gridCellTapped: GridCell): Set<Pair<Int, Int>> {
        var positions = mutableSetOf<Pair<Int, Int>>()
        while (positions.size < numberOfMines) {
            val randomRow = (0 until numberOfRows).random()
            val randomColumn = (0 until cellsPerRow).random()
            Pair(randomRow, randomColumn).also { randomPosition ->
                if (!positions.contains(randomPosition) && randomPosition != gridCellTapped.indexPath) {
                    positions.add(randomPosition)
                }
            }
        }
        return positions
    }

    private fun randomlyDistributeMines(gridCellTapped: GridCell) {
        randomMinePositions(gridCellTapped).also { positions ->
            gridCells.forEach { cell ->
                cell.hasMine = positions.contains(cell.indexPath)
            }
        }
    }

    private fun setUpGridCells() {
        gridCells = mutableListOf()
        (0 until numberOfRows).forEach { row ->
            (0 until cellsPerRow).forEach { column ->
                gridCells.add(GridCell(indexPath = Pair(row, column)))
            }
        }
    }

    fun gridCellTapped(gridCell: GridCell) {
        if (gridCell.uncovered || gridCell.hasFlag) return

        if (!timerStarted) {
            randomlyDistributeMines(gridCell)
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
        return getSurroundingCells(cell).count {it.hasMine }.toString()
    }

    private fun getSurroundingCells(cell: GridCell): List<GridCell> {
        var surroundingCells: MutableList<GridCell> = mutableListOf()
        ( (cell.indexPath.first - 1)..(cell.indexPath.first + 1) ).forEach { row ->
            ( (cell.indexPath.second - 1)..(cell.indexPath.second + 1) ).forEach { column ->
                if (!isOutOfBounds(Pair(row, column)) && !isAtSelectedCell(selectedCell = cell, indexPath = Pair(row, column))) {
                    gridCells.find { it.indexPath == Pair(row, column) }?.let { cellToAdd ->
                        surroundingCells.add(cellToAdd)
                    }
                }
            }
        }
        return surroundingCells
    }

    private fun isOutOfBounds(indexPath: Pair<Int, Int>): Boolean {
        return (indexPath.first < 0 ||
                indexPath.second < 0 ||
                indexPath.first >= numberOfRows ||
                indexPath.second >= cellsPerRow)
    }

    private fun isAtSelectedCell(selectedCell: GridCell, indexPath: Pair<Int, Int>): Boolean {
        return (selectedCell.indexPath.first == indexPath.first &&
                selectedCell.indexPath.second == indexPath.second)
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