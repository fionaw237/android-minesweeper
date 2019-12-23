package com.example.android_minesweeper

fun String.getDifficultyEnum() : Difficulty? {
    return when (this) {
        "Beginner" -> Difficulty.BEGINNER
        "Intermediate" -> Difficulty.INTERMEDIATE
        "Advanced" -> Difficulty.ADVANCED
        else -> null
    }
}