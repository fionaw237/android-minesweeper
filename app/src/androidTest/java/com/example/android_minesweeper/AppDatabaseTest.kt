package com.example.android_minesweeper

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android_minesweeper.models.AppDatabase
import com.example.android_minesweeper.models.HighScore
import com.example.android_minesweeper.models.HighScoreDao
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var highScoreDao: HighScoreDao
    private lateinit var db: AppDatabase
    private lateinit var highScores: List<HighScore>

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        highScoreDao = db.highScoreDao

        highScores = listOf(
            HighScore(difficulty = "Beginner", name = "Eva", time = "22"),
            HighScore(difficulty = "Beginner", name = "Merlin", time = "24"),
            HighScore(difficulty = "Intermediate", name = "Herman", time = "31"),
            HighScore(difficulty = "Advanced", name = "Sparkles", time = "42"),
            HighScore(difficulty = "Intermediate", name = "Belle", time = "32"),
            HighScore(difficulty = "Beginner", name = "Frankles", time = "19")
        )
        highScores.forEach { highScoreDao.insert(it) }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testGetByDifficulty() {
        assertEquals(highScoreDao.getByDifficulty("Beginner").size, 3)
        assertEquals(highScoreDao.getByDifficulty("Intermediate").size, 2)
        assertEquals(highScoreDao.getByDifficulty("Advanced").size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun testClearAll() {
        highScoreDao.clear()
        listOf("Beginner", "Intermediate", "Advanced").forEach {
            assertEquals(highScoreDao.getByDifficulty(it).size, 0)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateEntry() {
        val evaScore = highScoreDao.getByDifficulty("Beginner").find { it.name == "Eva" }
        evaScore!!.time = "20"
        highScoreDao.update(evaScore)
        highScoreDao.getByDifficulty("Beginner").filter { it.name == "Eva" }.also { updatedScore ->
            assertEquals(updatedScore[0].time, "20")
        }
    }
}