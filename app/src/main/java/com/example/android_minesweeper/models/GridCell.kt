package com.example.android_minesweeper.models

class GridCell(val indexPath: Pair<Int, Int>) {

    var hasMine: Boolean = false
    var hasFlag: Boolean = false
    var uncovered: Boolean = false
    var cellClickedForGameOver: Boolean = false
    var clickable = true
    var minesInVicinity: String = ""

    fun configureForGameOver() {
        clickable = false
        if (hasMine && !hasFlag) {
            uncovered = true
        } //else if (!cell.hasMine && cell.hasFlag) {

        // }
    }

}