package com.snake.cocoon.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
//@Entity(tableName = "TopStoriesTable")
@Entity(tableName = "TopStoriesTable", indices = [Index(value = ["title","image"], unique = true)])
class TopStoriesTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "image")
    var image: String = ""

    @ColumnInfo(name = "publish_date")
    var publishDate: String = ""

    @ColumnInfo(name = "url")
    var mainUrl: String = ""

    constructor(title: String, description: String, image: String, publishDate: String,mainUrl:String) {
        this.title = title
        this.description = description
        this.image = image
        this.publishDate = publishDate
        this.mainUrl=mainUrl
    }
}