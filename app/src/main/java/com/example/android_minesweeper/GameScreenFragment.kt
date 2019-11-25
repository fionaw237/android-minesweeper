package com.example.android_minesweeper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android_minesweeper.databinding.GameScreenBinding

class GameScreenFragment : Fragment() {

    private lateinit var binding: GameScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_screen, container, false)
        return binding.root
    }
}