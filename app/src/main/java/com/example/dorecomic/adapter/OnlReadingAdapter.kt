package com.example.dorecomic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.database.Page
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.cardview_page.view.*

class OnlReadingAdapter(var context: Context, var ls: ArrayList<Page>) :
    RecyclerView.Adapter<OnlReadingAdapter.ReadingHolder>() {

    private lateinit var rootView: View
    private var curPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingHolder {
        rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cardview_page, parent, false)
        return ReadingHolder(rootView)
    }

    override fun onBindViewHolder(holder: ReadingHolder, position: Int) {
        val pageRef = Firebase.storage.reference.child(ls[position].path)

        Glide
            .with(context)
            .load(pageRef)
            .fitCenter()
            .placeholder(R.drawable.default_comic)
            .into(holder.pageView)

    }

    override fun getItemCount(): Int {
        return ls.size
    }

    fun addNewPage(newPage: Page){
        ls.add(newPage)
        notifyItemInserted(ls.size)
    }


    inner class ReadingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageView: ImageView = itemView.page_comic


    }

}