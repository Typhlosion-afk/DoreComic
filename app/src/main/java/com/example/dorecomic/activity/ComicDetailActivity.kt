package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dorecomic.R
import com.example.dorecomic.fragment.ComicDetailFragment
import com.example.dorecomic.utilities.COMIC_PATH

class ComicDetailActivity : AppCompatActivity() {

    private lateinit var mComicPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_chapter)

        getData()
        initFragment()
    }

    private fun initFragment() {
        val comicDetailFragment = ComicDetailFragment()

        val comicDetailBundle = Bundle()
        comicDetailBundle.putString(COMIC_PATH, mComicPath)
        comicDetailFragment.arguments = comicDetailBundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_fragment_top, comicDetailFragment)
            .commit()
    }

    private fun getData() {
        mComicPath = intent.getStringExtra(COMIC_PATH) ?: ""
        Log.d("TAG", "getData: $mComicPath")
    }
}