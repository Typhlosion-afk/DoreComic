package com.example.dorecomic.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListChapterAdapter
import com.example.dorecomic.model.Chapter
import com.example.dorecomic.model.Comic
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.ComicDAO
import com.example.dorecomic.utilities.COMIC_PATH

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ComicDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var dao: ComicDAO

    private lateinit var rootView: View
    private lateinit var mComic: Comic
    private var mListChapter = ArrayList<Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.fragment_detail_comic, container, false)

        initData()
        initView()

        return rootView
    }

    private fun initData(){
        dao = AppDatabase.getInstance(rootView.context).comicDAO()
        val comicPath : String = arguments?.getString(COMIC_PATH) ?: ""
        mComic = dao.getComic(comicPath)
        Log.d("TAG", "initData: ${mComic.name}")


        mListChapter.addAll(dao.getListChapterOf(comicPath))
        Log.d("TAG", "initData: ${mListChapter.size}")

    }

    private fun initView(){
        val coverView = rootView.findViewById<ImageView>(R.id.cover_image_detail)
        val comicName = rootView.findViewById<TextView>(R.id.comic_name_detail)

        comicName.text = mComic.name
        Glide
            .with(activity as Context)
            .load(mComic.cover)
            .fitCenter()
            .into(coverView)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.list_chapter_recycler)
        val listChapterAdapter = ListChapterAdapter(mListChapter, rootView.context)
        LinearLayoutManager(rootView.context).apply {
            recyclerView.layoutManager = this
        }
        recyclerView.adapter = listChapterAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ComicDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}