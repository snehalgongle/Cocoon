package com.snake.cocoon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TopStoriesTable::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topStoriesDao(): TopStoriesDao

    companion object {

        private var appDatabase: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase::class.java, "TopStories.db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return appDatabase!!
        }
    }
}