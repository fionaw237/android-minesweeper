package com.example.android_minesweeper.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.R
import com.example.android_minesweeper.databinding.WelcomeScreenBinding
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeScreenFragment : Fragment() {

    private lateinit var binding: WelcomeScreenBinding
    private lateinit var viewModel: WelcomeScreenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.welcome_screen, container, false)
        binding.fragment = this
        viewModel = ViewModelProviders.of(this).get(WelcomeScreenViewModel::class.java)
        binding.welcomeScreenViewModel = viewModel
        binding.bestTimesButton.setOnClickListener {
            view?.findNavController()?.navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToBestTimesFragment())
        }
        return binding.root
    }

    fun difficultySelected(difficulty: Difficulty) {
        view?.findNavController()?.navigate(
            WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToGameScreenFragment(difficulty)
        )
    }

}