package com.example.android_minesweeper.screens.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_minesweeper.Difficulty

class WelcomeScreenViewModel : ViewModel() {

    private val _navigateToHighScores = MutableLiveData<Difficulty>()
    private val _navigateToGameScreen = MutableLiveData<Difficulty>()

    val navigateToHighScores: LiveData<Difficulty>
    get() = _navigateToHighScores

    val navigateToGameScreen: LiveData<Difficulty>
    get() = _navigateToGameScreen

    fun doneNavigatingToHighScores() {
        _navigateToHighScores.value = null
    }

    fun doneNavigatingToGameScreen() {
        _navigateToGameScreen.value = null
    }

    fun highScoresButtonPressed() {
        _navigateToHighScores.value = Difficulty.BEGINNER
    }

    fun difficultySelected(difficulty: Difficulty) {
        _navigateToGameScreen.value = difficulty
    }
}