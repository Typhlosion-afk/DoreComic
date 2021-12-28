package com.example.dorecomic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.activity.ReadingActivity
import com.example.dorecomic.model.Chapter
import kotlinx.android.synthetic.main.cardview_vol.view.*

class ListChapterAdapter (private val list : ArrayList<Chapter>, val context: Context)
    : RecyclerView.Adapter<ListChapterAdapter.ChapterHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_vol, parent, false)
        return ChapterHolder(rootView)
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        holder.chapterName.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val chapterName: TextView = itemView.txt_name_chapter
        init {
            itemView.setOnClickListener{
                val intent = Intent(context, ReadingActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}