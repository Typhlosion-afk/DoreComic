package com.example.dorecomic.fragment.device

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListChapterAdapter
import com.example.dorecomic.model.Chapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChapterDetailFragment : Fragment() {

    private lateinit var rootView: View

    private var mListChapter = ArrayList<Chapter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_detail_chapter, container, false)

        initData()
        initView()

        return rootView
    }

    private fun initData(){
        Log.d("TAG", "arguments: $arguments")
        arguments?.let {
            mListChapter.addAll(it.getSerializable("list") as List<Chapter>)
        }
    }

    private fun initView(){

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.list_chapter_recycler)
        val listChapterAdapter = ListChapterAdapter(mListChapter, rootView.context)
        LinearLayoutManager(rootView.context).apply {
            recyclerView.layoutManager = this
        }
        recyclerView.adapter = listChapterAdapter
    }


}