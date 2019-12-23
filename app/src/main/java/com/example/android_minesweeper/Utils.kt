package com.example.android_minesweeper

fun Difficulty.getDifficultyString(): String {
    return when (this) {
        Difficulty.BEGINNER -> "Beginner"
        Difficulty.INTERMEDIATE -> "Intermediate"
        Difficulty.ADVANCED -> "Advanced"
    }
}