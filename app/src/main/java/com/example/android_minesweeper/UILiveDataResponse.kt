package com.example.android_minesweeper

sealed class UILiveDataResponse {

    object NavigateToHighScores : UILiveDataResponse()
    object StartTimer : UILiveDataResponse()
    object ShowNoFlagsMessage : UILiveDataResponse()
    data class NavigateToGameScreen(val difficulty: Difficulty) : UILiveDataResponse()
}