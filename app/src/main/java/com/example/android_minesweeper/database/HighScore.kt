package com.example.android_minesweeper.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "difficulty")
    var difficulty: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "time")
    var time: String = "0"
)