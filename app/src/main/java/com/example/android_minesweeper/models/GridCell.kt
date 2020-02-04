package com.example.android_minesweeper.models

class GridCell(
    var hasMine: Boolean = false,
    var hasFlag: Boolean = false,
    var uncovered: Boolean = false,
    val positionInGrid: Int,
    var cellClickedForGameOver: Boolean = false) {

    fun configureForGameOver() {
        if (hasMine && !hasFlag) {
            uncovered = true
        } //else if (!cell.hasMine && cell.hasFlag) {

        // }
    }
}