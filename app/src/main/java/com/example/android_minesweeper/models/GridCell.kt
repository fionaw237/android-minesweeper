package com.example.android_minesweeper.models

class GridCell(
    var hasMine: Boolean = false,
    var hasFlag: Boolean = false,
    var uncovered: Boolean = false
)