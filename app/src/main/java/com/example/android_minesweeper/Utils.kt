package com.example.android_minesweeper

fun String.getDifficultyEnum() : Difficulty? {
    return when (this) {
        "Beginner" -> Difficulty.BEGINNER
        "Intermediate" -> Difficulty.INTERMEDIATE
        "Advanced" -> Difficulty.ADVANCED
        else -> null
    }
}

fun Long.convertMillisecondsToMinutesAndSecondsString(): String {
    val timeInSeconds = this / 1000
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60
    val minutesString = if (minutes < 10) "0${minutes}" else "${minutes}"
    val secondsString = if (seconds < 10) "0${seconds}" else "${seconds}"
    return "${minutesString}:${secondsString}"
}