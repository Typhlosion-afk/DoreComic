package com.example.dorecomic.model.database

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromTimestamp(timestamp: Long): String {
        return timestamp.let { FORMATTER.format(timestamp) }
    }

    @TypeConverter
    fun dateToTimestamp(time: String) {
        return time.let { FORMATTER.parse(it)?.time }
    }

    companion object {
        @SuppressLint("SimpleDateFormat")
        val FORMATTER = SimpleDateFormat("dd-mm-yyyy")
    }
}