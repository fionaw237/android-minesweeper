package com.example.android_minesweeper.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android_minesweeper.R
import com.example.android_minesweeper.UILiveDataResponse
import com.example.android_minesweeper.databinding.GameScreenBinding
import com.example.android_minesweeper.screens.welcome.WelcomeScreenFragmentDirections

class GameScreenFragment : Fragment() {

    private lateinit var binding: GameScreenBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_screen, container, false)
        viewModelFactory = GameViewModelFactory(GameScreenFragmentArgs.fromBundle(arguments!!).difficulty)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.responseLiveData.observe(this, Observer { response ->
            response?.let { result ->
                when (result) {
                    is UILiveDataResponse.StartTimer -> {
                        binding.root.findViewById<Chronometer>(R.id.timer)?.start()
                    }
                    else -> {}
                }
            }
        })


        return binding.root
    }
}