package com.example.android_minesweeper

sealed class UILiveDataResponse {

    object NavigateToHighScores : UILiveDataResponse()
    data class NavigateToGameScreen(val difficulty: Difficulty) : UILiveDataResponse()
}