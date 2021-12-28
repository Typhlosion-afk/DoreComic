package com.example.dorecomic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.activity.ListChapterActivity
import com.example.dorecomic.activity.ReadingActivity
import com.example.dorecomic.model.Comic
import kotlinx.android.synthetic.main.cardview_vol_grid.view.*

class ListComicAdapter (private val list: ArrayList<Comic>, var mContext: Context)
    : RecyclerView.Adapter<ListComicAdapter.ComicHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_vol_grid, parent, false)
        return ComicHolder(rootView)
    }

    override fun onBindViewHolder(holder: ComicHolder, position: Int) {
        holder.comicName.text = list[position].name

    }

    override fun getItemCount(): Int = list.size

    inner class ComicHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val comicName: TextView = itemView.txt_name_vol_grid
        init {
            itemView.setOnClickListener{
                val intent: Intent = Intent(mContext, ListChapterActivity::class.java).apply {
                    putExtra("comic", list[adapterPosition])
                }
                mContext.startActivity(intent)
            }
        }

    }
}