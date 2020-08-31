package com.example.android_minesweeper.screens.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.GridCellBinding
import com.example.android_minesweeper.models.GridCell
import com.example.android_minesweeper.view_models.GameViewModel
import kotlinx.android.synthetic.main.grid_cell.view.*

class GameBoardAdapter(val gameViewModel: GameViewModel) : RecyclerView.Adapter<GameBoardAdapter.GridCellViewHolder>() {

    var data = listOf<GridCell>()
    set(value) {
        field = value
        notifyItemRangeChanged(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridCellViewHolder {
        DataBindingUtil.inflate<GridCellBinding>(LayoutInflater.from(parent.context), R.layout.grid_cell, parent, false).let { binding ->
            val size = calculateSizeOfView(parent.context)
            val margin = 24
            val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size - margin, size - margin))
            layoutParams.bottomMargin = 4
            binding.root.layoutParams = layoutParams
            return GridCellViewHolder(binding)
        }
    }

    private fun calculateSizeOfView(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels
        return (dpWidth / gameViewModel.cellsPerRow)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GridCellViewHolder, position: Int) {
        holder.bind.gameViewModel = gameViewModel
        holder.bind.gridCell = data[position]
        holder.bind.root.grid_cell_image_button.setOnLongClickListener {
            gameViewModel.handleLongPress(data[position])
            true
        }
    }

    class GridCellViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        lateinit var bind: GridCellBinding

        constructor(bind: GridCellBinding) : this(bind.root) {
            this.bind = bind
        }
    }

}