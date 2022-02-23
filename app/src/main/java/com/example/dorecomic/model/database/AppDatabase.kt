package com.example.dorecomic.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dorecomic.utilities.DATABASE_NAME

@Database(entities = [Comic::class, Chapter::class, Page::class, History::class], version = 2)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun comicDAO(): ComicDAO

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context.applicationContext).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}
