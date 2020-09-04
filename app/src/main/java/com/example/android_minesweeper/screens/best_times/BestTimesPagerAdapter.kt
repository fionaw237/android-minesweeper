package com.example.android_minesweeper.screens.best_times

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.BestTimesPageBinding
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.view_models.HighScoresViewModel

class BestTimesPagerAdapter constructor(val viewModel: HighScoresViewModel): RecyclerView.Adapter<BestTimesPagerAdapter.PageViewHolder>() {

    var bestTimes: MutableList<HighScore> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageViewHolder {
        return PageViewHolder(
            DataBindingUtil.inflate<BestTimesPageBinding>(LayoutInflater.from(parent.context), R.layout.best_times_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.binding.viewModel = viewModel
        holder.binding.highScores = bestTimes
    }

    override fun getItemCount() = Difficulty.values().count()

    class PageViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        lateinit var binding: BestTimesPageBinding

        constructor(binding: BestTimesPageBinding) : this(binding.root) {
            this.binding = binding
        }
    }
}
