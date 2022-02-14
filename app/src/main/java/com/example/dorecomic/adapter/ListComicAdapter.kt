package com.example.dorecomic.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.activity.ListChapterActivity
import com.example.dorecomic.model.Comic
import kotlinx.android.synthetic.main.cardview_grid_comic.view.*
import kotlinx.android.synthetic.main.cardview_file.view.*

class ListComicAdapter (private val list: ArrayList<Comic>, var mContext: Context)
    : RecyclerView.Adapter<ListComicAdapter.ComicHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_grid_comic, parent, false)
        return ComicHolder(rootView)
    }

    override fun onBindViewHolder(holder: ComicHolder, position: Int) {
        holder.comicName.text = list[position].name

        Glide
            .with(mContext)
            .load(list[position].cover)
            .fitCenter()
            .into(holder.comicImg)
    }

    override fun getItemCount(): Int = list.size

    inner class ComicHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val comicName: TextView = itemView.txt_name_vol_grid
        val comicImg: ImageView = itemView.cover_image
        init {
            itemView.setOnClickListener{
                val intent: Intent = Intent(mContext, ListChapterActivity::class.java).apply {
                    putExtra("comic", list[adapterPosition])
                }

                val option = ActivityOptions
                    .makeSceneTransitionAnimation(mContext as Activity?,
                    Pair.create(comicImg, "comic_img_trans"),)

                mContext.startActivity(intent, option.toBundle())
            }
        }

    }
}