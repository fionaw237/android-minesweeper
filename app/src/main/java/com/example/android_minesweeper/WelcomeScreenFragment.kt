package com.example.android_minesweeper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android_minesweeper.databinding.WelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    private lateinit var binding: WelcomeScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.welcome_screen, container, false)

        binding.beginnerButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_welcomeScreenFragment_to_gameScreenFragment)
        }

        binding.intermediateButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_welcomeScreenFragment_to_gameScreenFragment)
        }

        binding.advancedButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_welcomeScreenFragment_to_gameScreenFragment)
        }

        binding.bestTimesButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_welcomeScreenFragment_to_bestTimesFragment)
        }

        return binding.root
    }



}