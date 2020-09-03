package com.example.android_minesweeper.view_models

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.example.android_minesweeper.*
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.models.HighScoreDao
import com.example.android_minesweeper.models.HighScoresRepository
import kotlinx.coroutines.*

class GameViewModel(private val difficulty: Difficulty, highScoreDao: HighScoreDao) : BaseViewModel() {

    var responseLiveData = MutableLiveData<UILiveDataResponse>()
    private val highScoresRepository = HighScoresRepository(highScoreDao)

    var numberOfMines: Int = 0
        set(value) {
            field = value
            flagsRemaining = value.toString()
        }

    private val numberOfRows = 8
    val cellsPerRow = 8

    var timerStarted = false
    var gameTime = 0L

    @Bindable
    var gameState = GameState.RUNNING
    set(value) {
        field = value
        notifyPropertyChanged(BR.gameState)
    }

    @Bindable
    var flagsRemaining: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.flagsRemaining)
    }

    @Bindable
    var gridCells: MutableList<GridCell> = mutableListOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.gridCells)
        }

    init {
        setUpGame()
    }

    fun doneNavigating() {
        responseLiveData.value = null
    }

    private fun setUpGame() {
        timerStarted = false
        gameTime = 0L
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
            responseLiveData.value = UILiveDataResponse.StartTimer
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

        if (isGameWon()) {
            handleGameWon()
        } else {
            // Play sound
        }
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
        var cellsChecked = mutableSetOf(cell)
        var cellsWithZeroMinesInVicinity = mutableSetOf(cell)

        while (cellsWithZeroMinesInVicinity.isNotEmpty()) {
            val cellsToCheck = cellsWithZeroMinesInVicinity.toMutableSet()
            cellsWithZeroMinesInVicinity = mutableSetOf()
            cellsToCheck.forEach { cellToCheck ->
                val adjacentCells = getSurroundingCells(cellToCheck)
                // loop through adjacent index paths which have not already been checked
                adjacentCells.filter { !cellsChecked.contains(it) }
                    .forEach { adjacentCell ->
                        cellsChecked.add(adjacentCell)
                        numberOfMinesInVicinityOfCell(adjacentCell).also {  mines ->
                            if (numberOfMinesInVicinityOfCell(adjacentCell) == "0") {
                                cellsWithZeroMinesInVicinity.add(adjacentCell)
                            }
                            if (!adjacentCell.hasFlag) {
                                gridCells.find { it == adjacentCell }?.let { cell ->
                                    cell.uncovered = true
                                    cell.minesInVicinity = mines
                                }
                            }
                        }

                    }
            }
        }

    }

    private fun refreshGridCells(updatedGridCells: MutableList<GridCell>) {
        gridCells = updatedGridCells
    }

    private fun gameOver(clickedCell: GridCell) {
        gameState = GameState.GAME_OVER
        clickedCell.cellClickedForGameOver = true
        configureCellsForGameOver()
        responseLiveData.value = UILiveDataResponse.StopTimer
        // play sound
        // configure reset button
        refreshGridCells(gridCells)
    }

    fun resetGrid() {
        gameState = GameState.RUNNING
        responseLiveData.value = UILiveDataResponse.ResetTimer
        setUpGame()
    }

    private fun configureCellsForGameOver() {
        gridCells.forEach { cell ->
            cell.configureForGameOver()
        }
    }

    private fun setFlagsLabel(flagAction: FlagAction) {
        flagsRemaining =  when (flagAction) {
            FlagAction.REMOVED -> (flagsRemaining.toInt() + 1).toString()
            FlagAction.ADDED -> (flagsRemaining.toInt() - 1).toString()
        }
    }

    fun handleLongPress(cell: GridCell) {
        if (!cell.hasFlag) {
            when (flagsRemaining) {
                "0" -> responseLiveData.value = UILiveDataResponse.ShowNoFlagsMessage
                else -> {
                    // Add flag
                    cell.hasFlag = true
                    setFlagsLabel(FlagAction.ADDED)
                }
            }
        } else {
            // Remove flag
            cell.hasFlag = false
            setFlagsLabel(FlagAction.REMOVED)
        }
        refreshGridCells(gridCells)
    }

    private fun isGameWon(): Boolean {
        val clickedCellCount = gridCells.filter { it.hasFlag || it.uncovered }.count()
        val totalCellsInGrid = numberOfRows * cellsPerRow
        return (clickedCellCount == totalCellsInGrid - flagsRemaining.toInt())
    }

    private fun handleGameWon() {
        gameState = GameState.WON
        disableCellInteraction()
        responseLiveData.value = UILiveDataResponse.StopTimer
        responseLiveData.value = UILiveDataResponse.ShowGameWonMessage(gameTime.convertMillisecondsToMinutesAndSecondsString())
    }

    private fun disableCellInteraction() {
        gridCells.forEach { cell ->
            cell.disable()
        }
    }

    fun gameWonAlertButtonPressed(enteredName: String?) {
        enteredName?.let { name ->
            GlobalScope.launch {
                highScoresRepository.storeNewHighScore(HighScore(name = name, time = gameTime, difficulty = difficulty.value))
                withContext(Dispatchers.Main) {
                    responseLiveData.value = UILiveDataResponse.NavigateToHighScores(difficulty, null)
                }
            }
        }
    }

    fun checkForNewBestTime(): Boolean = runBlocking {
             highScoresRepository.isNewBestTime(gameTime, difficulty)
        }

}