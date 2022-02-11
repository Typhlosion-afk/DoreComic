package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dorecomic.R
import com.example.dorecomic.fragment.device.ComicDetailFragment
import com.example.dorecomic.fragment.device.ChapterDetailFragment
import com.example.dorecomic.model.Chapter
import com.example.dorecomic.model.Comic
import java.io.File

class ListChapterActivity : AppCompatActivity() {

    lateinit var mComic: Comic

    lateinit var mComicPath: String

    private var mListChapter = ArrayList<Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chapter)

        getData()
        initFragment()
    }

    private fun initFragment() {
        val comicDetailFragment = ComicDetailFragment()
        val chapterListFragment = ChapterDetailFragment()

        val listChapterBundle = Bundle()
        listChapterBundle.putSerializable("list", mListChapter)
        chapterListFragment.arguments = listChapterBundle

        val comicDetailBundle = Bundle()
        comicDetailBundle.putSerializable("comic_detail", mComic)
        comicDetailFragment.arguments = comicDetailBundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_bot, chapterListFragment)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_top, comicDetailFragment)
            .commit()


    }

    private fun getData(){
        mComic = intent.getSerializableExtra("comic") as Comic
        mComicPath = mComic.path
        val comicDir = File(mComicPath)
        for (f: File in comicDir.listFiles()!!){
            if(f.name != "cover") {
                mListChapter.add(Chapter(f.name, f.absolutePath))
            }
        }


    }
}