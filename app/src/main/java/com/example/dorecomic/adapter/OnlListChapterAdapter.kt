package com.example.dorecomic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.activity.OnlineReadingActivity
import com.example.dorecomic.activity.ReadingActivity
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.utilities.CHAPTER_PATH

class OnlListChapterAdapter (private val list: ArrayList<Chapter>, val context: Context) :
    RecyclerView.Adapter<OnlListChapterAdapter.ChapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val rootView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_vol, parent, false)
        return ChapterHolder(rootView)
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        holder.chapterName.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addNewChapter(newChapter: Chapter){
        list.add(newChapter)
        notifyItemInserted(list.size)
    }

    inner class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chapterName: TextView = itemView.findViewById(R.id.txt_name_chapter)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, OnlineReadingActivity::class.java)
                intent.putExtra(CHAPTER_PATH, list[absoluteAdapterPosition].path)
                intent.putExtra("pos", absoluteAdapterPosition)
                intent.putExtra("list_chapter", list)
                context.startActivity(intent)
            }
        }
    }

}