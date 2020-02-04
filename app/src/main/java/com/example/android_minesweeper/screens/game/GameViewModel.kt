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
        return getSurroundingCells(cell).count {it.hasMine }.toString()
    }

    private fun getSurroundingCells(cell: GridCell): List<GridCell> {
        //TODO: make position a 2D quantity like an index path!
        var surroundingCells: MutableList<GridCell> = mutableListOf()

//        if (!isCellOnLeftEdge(cell)) {
//            surroundingCells.add(gridCells.find { it.positionInGrid == cell.positionInGrid - 1 }!!)
//        }
//        if (!isCellOnRightEdge(cell)) {
//             surroundingCells.add(gridCells.find { it.positionInGrid == cell.positionInGrid + 1 }!!)
//        }
//        if (!isCellOnTopEdge(cell)) {
//            val potentialCellsAbove = gridCells.filter { ( (cell.positionInGrid - cellsPerRow - 1)..(cell.positionInGrid - cellsPerRow + 1) ).contains(it.positionInGrid) }
//            surroundingCells.addAll(potentialCellsAbove.filter { cellIsInRowAbove(it, cell) })
//        }
//        if (!isCellOnBottomEdge(cell)) {
//            val potentialCellsBelow = gridCells.filter { ( (cell.positionInGrid + cellsPerRow - 1)..(cell.positionInGrid + cellsPerRow + 1) ).contains(it.positionInGrid) }
//            surroundingCells.addAll(potentialCellsBelow.filter { cellIsInRowBelow(it, cell) })
//        }

        return surroundingCells
    }

//    private fun cellIsInRowAbove(cellToCheck: GridCell, referenceCell: GridCell): Boolean {
//        val positionOfFirstCellInRowAbove = (referenceCell.positionInGrid % cellsPerRow) * numberOfRows
//        val positionOfLastCellInRowAbove = positionOfFirstCellInRowAbove + cellsPerRow
//        return (positionOfFirstCellInRowAbove..positionOfLastCellInRowAbove).contains(cellToCheck.positionInGrid)
//    }
//
//    private fun cellIsInRowBelow(cellToCheck: GridCell, referenceCell: GridCell): Boolean {
//        val positionOfFirstCellInRowBelow = (referenceCell.positionInGrid % cellsPerRow) * numberOfRows
//        val positionOfLastCellInRowBelow = positionOfFirstCellInRowBelow + cellsPerRow
//        return (positionOfFirstCellInRowBelow..positionOfLastCellInRowBelow).contains(cellToCheck.positionInGrid)
//    }
//
//    private fun isCellOnLeftEdge(cell: GridCell): Boolean {
//        return (cell.positionInGrid - 1) % cellsPerRow == 0
//    }
//
//    private fun isCellOnRightEdge(cell: GridCell): Boolean {
//        return cell.positionInGrid % cellsPerRow == 0
//    }
//
//    private fun isCellOnTopEdge(cell: GridCell): Boolean {
//        return cell.positionInGrid <= cellsPerRow
//    }
//
//    private fun isCellOnBottomEdge(cell: GridCell): Boolean {
//        return cell.positionInGrid >= (numberOfRows * cellsPerRow) - cellsPerRow
//    }

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