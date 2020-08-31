package com.example.android_minesweeper.models

import android.content.Context
import androidx.room.*

@Database(entities = [HighScore::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val highScoreDao: HighScoreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val databaseName = "appDatabase"

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        databaseName
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }

    }
}