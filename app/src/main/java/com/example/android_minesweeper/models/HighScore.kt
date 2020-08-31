package com.example.android_minesweeper.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "difficulty")
    var difficulty: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "time")
    var time: String = "0"
)