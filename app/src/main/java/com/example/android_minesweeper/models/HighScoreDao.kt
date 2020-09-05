package com.example.android_minesweeper.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Insert(onConflict = REPLACE)
    fun insert(highScore: HighScore)

    @Query("SELECT * FROM high_scores WHERE difficulty = :difficulty")
    fun getByDifficulty(difficulty: String): List<HighScore>

    @Query("DELETE FROM high_scores")
    fun clear()
}