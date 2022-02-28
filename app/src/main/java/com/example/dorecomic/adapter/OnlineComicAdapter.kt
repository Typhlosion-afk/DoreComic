package com.example.dorecomic.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent

import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.activity.OnlComicDetailActivity
import com.example.dorecomic.model.database.Comic
import com.example.dorecomic.utilities.COMIC_PATH
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class OnlineComicAdapter(var mContext: Context, var mListComic: ArrayList<Comic>) :
    RecyclerView.Adapter<OnlineComicAdapter.OnlineComicHolder>() {

    private lateinit var rootView: View
    private lateinit var listCover: ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineComicHolder {
        rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_grid_comic, parent, false)
//        initData()
        return OnlineComicHolder(rootView)
    }

    override fun onBindViewHolder(holder: OnlineComicHolder, position: Int) {
        val comic = mListComic[position]
        holder.comicName.text = comic.name
        val image: StorageReference = Firebase
            .storage.reference.child(comic.cover)

        Glide
            .with(mContext)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.default_comic)
            .into(holder.comicImg)
    }

    fun addNewComic(newComic: Comic){
        mListComic.add(newComic)
        notifyItemInserted(mListComic.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Comic>){
        mListComic.clear()
        mListComic.addAll(newList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mListComic.size
    }

    inner class OnlineComicHolder(item: View) : RecyclerView.ViewHolder(item) {
        val comicName: TextView = itemView.findViewById(R.id.txt_name_vol_grid)
        val comicImg: ImageView = itemView.findViewById(R.id.cover_image)

        init {
            itemView.setOnClickListener {
                val intent: Intent = Intent(mContext, OnlComicDetailActivity::class.java).apply {
                    putExtra(COMIC_PATH, mListComic[absoluteAdapterPosition].path)
                }
                Log.d("TAG", ": ")
                Log.d("TAG", mListComic[absoluteAdapterPosition].path)
                val option = ActivityOptions
                    .makeSceneTransitionAnimation(
                        mContext as Activity?,
                        Pair.create(comicImg, "comic_img_trans"),
                    )
                mContext.startActivity(intent, option.toBundle())
            }
        }
    }
}