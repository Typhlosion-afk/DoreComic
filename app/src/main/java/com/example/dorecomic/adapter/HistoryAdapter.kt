package com.example.dorecomic.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.util.Pair
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.activity.ListChapterActivity
import com.example.dorecomic.model.Comic

class HistoryAdapter(val context: Context, val listHis: List<Comic>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    private lateinit var rootView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        rootView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_grid_comic, parent, false)
        return HistoryHolder(rootView)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.comicName.text = listHis[position].name

        Glide
            .with(context)
            .load(listHis[position].cover)
            .fitCenter()
            .into(holder.comicImg)
    }

    override fun getItemCount(): Int {
        return listHis.size
    }

    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val comicName: TextView = itemView.findViewById(R.id.txt_name_vol_grid)
        val comicImg: ImageView = itemView.findViewById(R.id.cover_image)
        init {
            itemView.setOnClickListener{
                val intent = Intent(context, ListChapterActivity::class.java).apply {
                    putExtra("comic", listHis[absoluteAdapterPosition])
                }
                val option = ActivityOptions
                    .makeSceneTransitionAnimation(context as Activity?,
                        Pair.create(comicImg, "comic_img_trans"),)
                context.startActivity(intent, option.toBundle())
            }

        }
    }
}