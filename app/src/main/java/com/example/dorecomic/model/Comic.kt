package com.example.dorecomic.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "comic_table")
data class Comic(
    @PrimaryKey
    @ColumnInfo(name = "comic_path")
    val path: String,

    @ColumnInfo(name = "comic_name")
    val name: String,

    @ColumnInfo(name = "comic_cover")
    val cover: String) : Serializable{

}