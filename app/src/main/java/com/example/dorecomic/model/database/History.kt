package com.example.dorecomic.model.database

import androidx.room.*
import java.util.*

@Entity(tableName = "history_table", indices = [Index(value = arrayOf("page"), unique = true)])
class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val idHis: Int = 0,

    @ColumnInfo(name = "date")
    val date: Long,

    @ColumnInfo(name = "page")
    val pagePath: String
) {
}