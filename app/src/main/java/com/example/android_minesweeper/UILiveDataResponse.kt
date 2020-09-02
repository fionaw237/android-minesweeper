package com.example.android_minesweeper

import com.example.android_minesweeper.models.HighScore

sealed class UILiveDataResponse {
    object StartTimer : UILiveDataResponse()
    object StopTimer : UILiveDataResponse()
    object ResetTimer : UILiveDataResponse()
    object ShowNoFlagsMessage : UILiveDataResponse()
    data class NavigateToGameScreen(val difficulty: Difficulty) : UILiveDataResponse()
    data class NavigateToHighScores(val difficulty: Difficulty, val newHighScore: HighScore? = null) : UILiveDataResponse()
    data class ShowGameWonMessage(val time: String) : UILiveDataResponse()
}