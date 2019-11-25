package com.example.android_minesweeper.screens.best_times

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.BestTimesScreenBinding

class BestTimesFragment : Fragment() {

    private lateinit var binding: BestTimesScreenBinding

    private lateinit var viewModel: BestTimesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.best_times_screen, container, false)
        viewModel = ViewModelProviders.of(this).get(BestTimesViewModel::class.java)
        return binding.root
    }
}