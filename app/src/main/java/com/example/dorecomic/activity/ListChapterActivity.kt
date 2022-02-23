package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dorecomic.R
import com.example.dorecomic.fragment.ComicDetailFragment
import com.example.dorecomic.model.Chapter
import com.example.dorecomic.model.Comic
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.ComicDAO
import com.example.dorecomic.utilities.COMIC_PATH

class ListChapterActivity : AppCompatActivity() {

    lateinit var mComic: Comic

    lateinit var mComicPath: String

    private var mListChapter = ArrayList<Chapter>()

    private lateinit var dao : ComicDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chapter)

        getData()
        initFragment()
    }

    private fun initFragment() {
        val comicDetailFragment = ComicDetailFragment()

        val comicDetailBundle = Bundle()
        comicDetailBundle.putString(COMIC_PATH, mComic.path)
        comicDetailFragment.arguments = comicDetailBundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_top, comicDetailFragment)
            .commit()
    }

    private fun getData(){
        dao =AppDatabase.getInstance(applicationContext).comicDAO()
        val comicPath : String = intent.getStringExtra(COMIC_PATH) ?: ""
        if(comicPath != "" ) {
            mComic = dao.getComic(comicPath)
            mListChapter.addAll(dao.getListChapterOf(comicPath))
        }
    }
}