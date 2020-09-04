package com.example.android_minesweeper

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.screens.game.GameBoardAdapter
import com.example.android_minesweeper.view_models.GameViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.screens.best_times.BestTimesPagerAdapter
import com.example.android_minesweeper.screens.best_times.HighScoresAdapter
import com.example.android_minesweeper.view_models.HighScoresViewModel

// ----- Data binding for game grid -----

@BindingAdapter("gameGrid")
fun setUpGameGrid(recyclerView: RecyclerView, gameViewModel: GameViewModel) {
    recyclerView.itemAnimator = null
    if (recyclerView.adapter == null) {
        recyclerView.adapter = GameBoardAdapter(gameViewModel)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, gameViewModel.cellsPerRow)
    }
}

@BindingAdapter("gridCells")
fun setGridCells(recyclerView: RecyclerView, gridCells: List<GridCell>) {
    (recyclerView.adapter as GameBoardAdapter).data = gridCells
}

@BindingAdapter("minesInVicinityLabelColour")
fun setMinesInVicinityLabelColour(textView: TextView, minesInVicinity: String) {
    if (minesInVicinity != "" && minesInVicinity != "0") {
        textView.setTextColor(
            when (minesInVicinity.toInt()) {
                1 -> Color.BLUE
                2 -> Color.GREEN
                3 -> Color.RED
                4 -> Color.parseColor("#800080")
                5 -> Color.MAGENTA
                6 -> Color.CYAN
                7 -> Color.BLACK
                else -> Color.GRAY
            }
        )
    }
}

@BindingAdapter("minesInVicinity", "uncovered", "hasMine", requireAll = true)
fun setMinesInVicinityLabelVisibility(textView: TextView, minesInVicinity: String, uncovered: Boolean, hasMine: Boolean) {
    textView.visibility = if (minesInVicinity != "0" && uncovered && !hasMine) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setFaceImage")
fun setFaceImage(imageButton: ImageButton, gameState: GameState) {
    imageButton.setBackgroundResource(
        when (gameState) {
            GameState.RUNNING -> R.drawable.ic_happy
            GameState.WON -> R.drawable.ic_cool
            GameState.GAME_OVER -> R.drawable.ic_sad
        }
    )
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

@BindingAdapter("setBestTimesPagerAdapter")
fun setBestTimesPagerAdapter(pager: ViewPager2, viewModel: HighScoresViewModel) {
    pager.adapter = BestTimesPagerAdapter(viewModel)
}