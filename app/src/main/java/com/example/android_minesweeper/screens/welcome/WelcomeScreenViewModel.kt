package com.example.android_minesweeper.screens.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_minesweeper.Difficulty
import com.example.android_minesweeper.UILiveDataResponse

class WelcomeScreenViewModel : ViewModel() {

    var responseLiveData = MutableLiveData<UILiveDataResponse>()

    fun doneNavigating() {
       responseLiveData.value = null
    }

    fun highScoresButtonPressed() {
        responseLiveData.value = UILiveDataResponse.NavigateToHighScores
    }

    fun difficultySelected(difficulty: Difficulty) {
        responseLiveData.value = UILiveDataResponse.NavigateToGameScreen(difficulty)
    }
}