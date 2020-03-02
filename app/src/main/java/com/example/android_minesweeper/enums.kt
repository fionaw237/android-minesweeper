package com.example.android_minesweeper

enum class Difficulty(val value: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

enum class FlagAction {
    ADDED,
    REMOVED
}

enum class GameState {
    RUNNING,
    WON,
    GAME_OVER
}