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
import kotlinx.android.synthetic.main.best_times_page.view.*

class BestTimesPagerAdapter constructor(val viewModel: HighScoresViewModel): RecyclerView.Adapter<BestTimesPagerAdapter.PageViewHolder>() {

    val bestTimes: MutableMap<Difficulty, MutableList<HighScore>> = mutableMapOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestTimesPagerAdapter.PageViewHolder {
        return PageViewHolder(
            DataBindingUtil.inflate<BestTimesPageBinding>(LayoutInflater.from(parent.context), R.layout.best_times_page, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestTimesPagerAdapter.PageViewHolder, position: Int) {
//        holder.binding.highScores = bestTimes[position]
    }

    override fun getItemCount() = 3

    class PageViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        lateinit var binding: BestTimesPageBinding
        var recyclerView: RecyclerView = view.best_times_recycler_view

        constructor(binding: BestTimesPageBinding) : this(binding.root) {
            this.binding = binding
        }
    }
}
