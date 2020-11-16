package com.snake.cocoon.data.local

import androidx.room.*

@Dao
interface TopStoriesDao {

    @Query("SELECT * FROM TopStoriesTable")
    fun getAll(): List<TopStoriesTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg topStoriesTable: TopStoriesTable)

    /*@Delete
    fun delete(vararg topStoriesTable: TopStoriesTable)*/
}