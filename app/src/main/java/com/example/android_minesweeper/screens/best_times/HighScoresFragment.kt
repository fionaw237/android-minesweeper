package com.example.android_minesweeper.screens.best_times

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android_minesweeper.R
import com.example.android_minesweeper.database.AppDatabase
import com.example.android_minesweeper.databinding.HighScoresScreenBinding

class HighScoresFragment : Fragment() {

    private lateinit var binding: HighScoresScreenBinding
    private lateinit var viewModel: HighScoresViewModel
    private lateinit var viewModelFactory: HighScoresViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.high_scores_screen, container, false)
        val dataSource = AppDatabase.getInstance(requireNotNull(this.activity).application).highScoreDao
        viewModelFactory = HighScoresViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HighScoresViewModel::class.java)
        binding.highScoresViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}