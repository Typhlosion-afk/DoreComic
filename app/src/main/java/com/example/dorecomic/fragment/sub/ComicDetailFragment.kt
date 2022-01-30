package com.example.dorecomic.fragment.sub

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.Comic

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ComicDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var comic: Comic

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
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_detail_comic, container, false)

        initData()
        initView()

        return rootView
    }

    private fun initData(){
        arguments?.let {
            comic = it.getSerializable("comic_detail") as Comic
        }
    }

    private fun initView(){
        val coverView = rootView.findViewById<ImageView>(R.id.cover_image_detail)
        val comicName = rootView.findViewById<TextView>(R.id.comic_name_detail)

        comicName.text = comic.name

        Glide
            .with(activity as Context)
            .load(comic.cover)
            .fitCenter()
            .into(coverView)
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