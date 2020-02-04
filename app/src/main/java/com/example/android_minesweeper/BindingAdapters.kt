package com.example.android_minesweeper

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.database.HighScore
import com.example.android_minesweeper.screens.best_times.HighScoresAdapter
import com.example.android_minesweeper.screens.best_times.HighScoresViewModel

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