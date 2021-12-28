package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListChapterAdapter
import com.example.dorecomic.model.Chapter
import com.example.dorecomic.model.Comic
import kotlinx.android.synthetic.main.activity_list_chapter.*
import java.io.File

class ListChapterActivity : AppCompatActivity() {

    lateinit var mComic: Comic

    lateinit var mComicPath: String

    private var mListChapter = ArrayList<Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chapter)

        getData()
        initView()
    }

    private fun initView() {
        var recyclerView = list_chapter_recycler

        if(mListChapter.size == 0){

        }else{
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                .apply {
                    recyclerView.layoutManager = this
                }
            recyclerView.adapter = ListChapterAdapter(mListChapter, this)

        }
    }

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