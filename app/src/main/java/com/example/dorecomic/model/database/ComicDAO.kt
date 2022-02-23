package com.example.dorecomic.model.database

import androidx.room.*
import com.example.dorecomic.model.Chapter
import com.example.dorecomic.model.Comic
import com.example.dorecomic.model.Page
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDAO {
    @Query("SELECT * FROM comic_table ORDER BY comic_path")
    fun getListComic(): List<Comic>

    @Query("SELECT * FROM chapter_table WHERE comic_path = :comicPath")
    fun getListChapterOf(comicPath: String): List<Chapter>

    @Query("SELECT * FROM page_table WHERE chapter_path = :chapterPath")
    fun getListPageOf(chapterPath: String): List<Page>

    @Query("SELECT * FROM comic_table WHERE comic_path = :comicPath")
    fun getComic(comicPath: String): Comic

    @Query("SELECT * FROM chapter_table WHERE chapter_path = :chapterPath")
    fun getChapter(chapterPath: String): Chapter

    @Query("SELECT * FROM page_table WHERE page_path = :pagePath")
    fun getPage(pagePath: String): Page

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListComic(list: List<Comic>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListChapter(list: List<Chapter>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListPage(list: List<Page>)

    @Query("DELETE FROM comic_table")
    fun deleteComic()

    @Query("DELETE FROM chapter_table")
    fun deleteChapter()

    @Query("DELETE FROM page_table")
    fun deletePage()

}