package com.example.dorecomic.model.database

import androidx.room.*
import java.io.Serializable

@Entity(
    tableName = "chapter_table",
    foreignKeys = [
        ForeignKey(
            entity = Comic::class,
            parentColumns = arrayOf("comic_path"),
            childColumns = arrayOf("comic_path"),
            onDelete = ForeignKey.CASCADE
        )]
)
class Chapter(
    @PrimaryKey
    @ColumnInfo(name = "chapter_path")
    val path: String,

    @ColumnInfo(name = "comic_path")
    val comicPath: String,

    @ColumnInfo(name = "chapter_name")
    val name: String
) : Serializable {
}