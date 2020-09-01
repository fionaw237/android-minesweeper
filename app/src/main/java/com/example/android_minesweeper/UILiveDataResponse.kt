package com.example.android_minesweeper

sealed class UILiveDataResponse {
    object StartTimer : UILiveDataResponse()
    object StopTimer : UILiveDataResponse()
    object ResetTimer : UILiveDataResponse()
    object ShowNoFlagsMessage : UILiveDataResponse()
    data class NavigateToGameScreen(val difficulty: Difficulty) : UILiveDataResponse()
    data class NavigateToHighScores(val difficulty: Difficulty) : UILiveDataResponse()
    data class ShowGameWonMessage(val time: String) : UILiveDataResponse()
}