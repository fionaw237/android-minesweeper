package com.example.android_minesweeper.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.WelcomeScreenBinding

class WelcomeScreenFragment : Fragment() {

    private lateinit var binding: WelcomeScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.welcome_screen, container, false)

        binding.beginnerButton.setOnClickListener {
            view?.findNavController()?.navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToGameScreenFragment(0))
        }

        binding.intermediateButton.setOnClickListener {
            view?.findNavController()?.navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToGameScreenFragment(1))
        }

        binding.advancedButton.setOnClickListener {
            view?.findNavController()?.navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToGameScreenFragment(2))
        }

        binding.bestTimesButton.setOnClickListener {
            view?.findNavController()?.navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToBestTimesFragment())
        }

        return binding.root
    }



}