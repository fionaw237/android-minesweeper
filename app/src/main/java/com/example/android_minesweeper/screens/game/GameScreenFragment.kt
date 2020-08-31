package com.example.android_minesweeper.screens.game

import android.app.AlertDialog
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android_minesweeper.R
import com.example.android_minesweeper.UILiveDataResponse
import com.example.android_minesweeper.databinding.GameScreenBinding
import com.example.android_minesweeper.view_models.GameViewModel

class GameScreenFragment : Fragment() {

    private lateinit var binding: GameScreenBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var viewModelFactory: GameViewModelFactory
    private lateinit var timer: Chronometer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.show()

        binding = DataBindingUtil.inflate(inflater, R.layout.game_screen, container, false)
        viewModelFactory = GameViewModelFactory(GameScreenFragmentArgs.fromBundle(arguments!!).difficulty)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        timer = binding.root.findViewById(R.id.timer)
        timer.setOnChronometerTickListener {
            updateGameTime()
        }

        viewModel.responseLiveData.observe(this, Observer { response ->
            response?.let { result ->
                when (result) {
                    is UILiveDataResponse.StartTimer -> {
                        startTimer()
                    }
                    is UILiveDataResponse.StopTimer -> {
                        stopTimer()
                    }
                    is UILiveDataResponse.ResetTimer -> {
                        resetTimer()
                    }
                    is UILiveDataResponse.ShowNoFlagsMessage -> {
                        with(AlertDialog.Builder(context)) {
                            setTitle("No flags left!")
                            setMessage("Remove an existing flag to place one elsewhere.")
                            setCancelable(false)
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.cancel()
                            }
                            show()
                        }
                    }
                    is UILiveDataResponse.ShowGameWonMessage -> {
                        with(AlertDialog.Builder(context)) {
                            setTitle("You won!")
                            setMessage("Your time was ${viewModel.gameTime / 1000} seconds.")
                            setCancelable(false)
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.cancel()
                            }
                            show()
                        }
                    }
                    else -> {}
                }
            }
        })


        return binding.root
    }

    private fun updateGameTime() {
        viewModel.gameTime = SystemClock.elapsedRealtime() - timer.base
    }

    private fun startTimer() {
        timer.base = SystemClock.elapsedRealtime()
        timer.start()
        viewModel.timerStarted = true
    }

    private fun stopTimer() {
        timer.stop()
        viewModel.timerStarted = false
    }

    private fun resetTimer() {
        timer.base = SystemClock.elapsedRealtime()
        stopTimer()
    }
}