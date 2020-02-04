package com.example.android_minesweeper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.screens.game.GameBoardAdapter
import com.example.android_minesweeper.screens.game.GameViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_minesweeper.database.HighScore
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.screens.best_times.HighScoresAdapter
import com.example.android_minesweeper.screens.best_times.HighScoresViewModel

// ----- Data binding for game grid -----

@BindingAdapter("gameGrid")
fun setUpGameGrid(recyclerView: RecyclerView, gameViewModel: GameViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = GameBoardAdapter(gameViewModel)
        recyclerView.layoutManager =
            GridLayoutManager(recyclerView.context, gameViewModel.cellsPerRow)
    }
}

@BindingAdapter("gridCells")
fun setGridCells(recyclerView: RecyclerView, gridCells: List<GridCell>) {
    (recyclerView.adapter as GameBoardAdapter).data = gridCells
}

// ----- Data binding for high scores -----

@BindingAdapter("highScoresAdapter")
fun setUpHighScoresRecyclerViewAdapter(recyclerView: RecyclerView, highScoresViewModel: HighScoresViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = HighScoresAdapter()
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }
}

@BindingAdapter("highScores")
fun setHighScoresData(recyclerView: RecyclerView, highScores: List<HighScore>) {
    (recyclerView.adapter as HighScoresAdapter).data = highScores
}