package com.example.android_minesweeper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.screens.game.GameBoardAdapter
import com.example.android_minesweeper.screens.game.GameViewModel

@BindingAdapter("gameGrid")
fun setUpGameGrid(recyclerView: RecyclerView, gameViewModel: GameViewModel) {
    if (recyclerView.adapter == null) {
        recyclerView.adapter = GameBoardAdapter(gameViewModel)
        //        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, viewModel.cellsPerRow)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).also { layoutManager ->
            layoutManager.orientation = RecyclerView.VERTICAL
        }

        gameViewModel.gridCells.observeForever {
            it?.let {
                (recyclerView.adapter as GameBoardAdapter).data = it
            }
        }
    }
}