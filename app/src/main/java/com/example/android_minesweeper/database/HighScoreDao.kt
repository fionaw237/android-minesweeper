package com.example.android_minesweeper.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HighScoreDao {
    @Insert
    fun insert(highScore: HighScore)

    @Update
    fun update(highScore: HighScore)

    @Query("SELECT * FROM high_scores WHERE difficulty = :difficulty")
    fun getByDifficulty(difficulty: String): List<HighScore>

    @Query("DELETE FROM high_scores")
    fun clear()
}