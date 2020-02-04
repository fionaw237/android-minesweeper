package com.example.android_minesweeper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.screens.game.GameBoardAdapter
import com.example.android_minesweeper.screens.game.GameViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_minesweeper.database.HighScore
import com.example.android_minesweeper.screens.best_times.HighScoresAdapter
import com.example.android_minesweeper.screens.best_times.HighScoresViewModel

@BindingAdapter("gameGrid")
fun setUpGameGrid(recyclerView: RecyclerView, gameViewModel: GameViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = GameBoardAdapter(gameViewModel)
        recyclerView.layoutManager =
            GridLayoutManager(recyclerView.context, gameViewModel.cellsPerRow)

        gameViewModel.gridCells.observeForever {
            it?.let {
                (recyclerView.adapter as GameBoardAdapter).data = it
            }
        }
    }
}

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