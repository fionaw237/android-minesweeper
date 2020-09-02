package com.example.android_minesweeper.screens.best_times

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.R
import com.example.android_minesweeper.convertMillisecondsToMinutesAndSecondsString
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.databinding.HighScoreCellBinding
import com.example.android_minesweeper.models.HighScoreDisplay

class HighScoresAdapter() : RecyclerView.Adapter<HighScoreViewHolder>() {

    var data = listOf<HighScore>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        return HighScoreViewHolder(
            DataBindingUtil.inflate<HighScoreCellBinding>(LayoutInflater.from(parent.context), R.layout.high_score_cell, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.bind.highScoreDisplayModel = HighScoreDisplay(highScore = data[position],  position = position + 1)
    }
}


class HighScoreViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    lateinit var bind: HighScoreCellBinding

    constructor(bind: HighScoreCellBinding) : this(bind.root) {
        this.bind = bind
    }
}