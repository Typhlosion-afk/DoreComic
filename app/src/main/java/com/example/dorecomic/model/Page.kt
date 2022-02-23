package com.example.dorecomic.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "page_table",
    foreignKeys = [
        ForeignKey(entity = Chapter::class,
            parentColumns = ["chapter_path"],
            childColumns = ["chapter_path"],
            onDelete = ForeignKey.CASCADE)])
data class Page(
    @PrimaryKey
    @ColumnInfo(name = "page_path")
    var path: String,

    @ColumnInfo(name = "chapter_path")
    val chapterPath: String,

    @ColumnInfo(name = "page_num")
    var num: Int,

    @ColumnInfo(name = "page_name")
    var name: String): Serializable{
}