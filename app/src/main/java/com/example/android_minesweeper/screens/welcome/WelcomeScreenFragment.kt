package com.example.android_minesweeper.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android_minesweeper.R
import com.example.android_minesweeper.UILiveDataResponse
import com.example.android_minesweeper.databinding.WelcomeScreenBinding
import com.example.android_minesweeper.view_models.WelcomeScreenViewModel

class WelcomeScreenFragment : Fragment() {

    private lateinit var binding: WelcomeScreenBinding
    private lateinit var viewModel: WelcomeScreenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as? AppCompatActivity)?.supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        binding = DataBindingUtil.inflate(inflater,
            R.layout.welcome_screen, container, false)
        viewModel = ViewModelProviders.of(this).get(WelcomeScreenViewModel::class.java)
        binding.welcomeScreenViewModel = viewModel

        viewModel.responseLiveData.observe(this, Observer { response ->
            response?.let { result ->
                this.findNavController().navigate(
                    if (result is UILiveDataResponse.NavigateToGameScreen) {
                        WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToGameScreenFragment(result.difficulty)
                    } else {
                        WelcomeScreenFragmentDirections.actionWelcomeScreenFragmentToBestTimesFragment()
                    }
                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}