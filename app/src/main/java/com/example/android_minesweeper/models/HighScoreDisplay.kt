package com.example.android_minesweeper.models

import com.example.android_minesweeper.convertMillisecondsToMinutesAndSecondsString

class HighScoreDisplay(val highScore: HighScore, position: Int) {
    val ranking = "$position"
    val name = highScore.name
    var time = highScore.time.convertMillisecondsToMinutesAndSecondsString()
}