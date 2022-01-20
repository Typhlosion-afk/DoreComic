package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dorecomic.R
import com.example.dorecomic.fragment.ChapterListFragment
import com.example.dorecomic.fragment.ComicDetailFragment
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
//        initView()
    }

    private fun initFragment() {
        val comicDetailFragment = ComicDetailFragment()
        val chapterListFragment = ChapterListFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_top, chapterListFragment)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_bot, comicDetailFragment)
            .commit()
    }

//    private fun initView() {
//        val recyclerView = list_chapter_recycler
//
//        if(mListChapter.size == 0){
//
//        }else{
//            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//                .apply {
//                    recyclerView.layoutManager = this
//                }
//            recyclerView.adapter = ListChapterAdapter(mListChapter, this)
//
//        }
//    }

    private fun getData(){
        mComic = intent.getSerializableExtra("comic") as Comic
        mComicPath = mComic.path
        val comicDir = File(mComicPath)
        for (f: File in comicDir.listFiles()!!){
            mListChapter.add(Chapter(f.name, f.absolutePath))
            Log.d("TAG", "getData: " + f.name)
        }


    }
}