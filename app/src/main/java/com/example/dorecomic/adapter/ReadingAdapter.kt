package com.example.dorecomic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.Page
import kotlinx.android.synthetic.main.cardview_file.view.*
import kotlinx.android.synthetic.main.cardview_page.view.*

class ReadingAdapter(var context: Context, var ls: List<Page>) : RecyclerView.Adapter<ReadingAdapter.ReadingHolder>() {

    private lateinit var rootView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingHolder {
        rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cardview_page, parent, false)
        return ReadingHolder(rootView)
    }

    override fun onBindViewHolder(holder: ReadingHolder, position: Int) {
            Glide
                .with(context)
                .load(ls[position].path)
                .fitCenter()
                .into(holder.pageView)

    }

    override fun getItemCount(): Int {
        return ls.size
    }

    inner class ReadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pageView: ImageView = itemView.page_comic

    }
}