package com.example.android_minesweeper.screens.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.GridCellBinding
import com.example.android_minesweeper.models.GridCell

class GameBoardAdapter(val gameViewModel: GameViewModel) : RecyclerView.Adapter<GameBoardAdapter.GridCellViewHolder>() {

    var data = listOf<GridCell>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridCellViewHolder {
        return GridCellViewHolder(
            DataBindingUtil.inflate<GridCellBinding>(LayoutInflater.from(parent.context), R.layout.grid_cell, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GridCellViewHolder, position: Int) {
        holder.bind.gameViewModel = gameViewModel
        holder.bind.gridCell = data[position]
    }

    class GridCellViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        lateinit var bind: GridCellBinding

        constructor(bind: GridCellBinding) : this(bind.root) {
            this.bind = bind
        }
    }
}