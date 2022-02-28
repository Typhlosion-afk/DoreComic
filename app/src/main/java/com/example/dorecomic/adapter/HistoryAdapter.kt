package com.example.dorecomic.adapter

import android.annotation.SuppressLint
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
import com.example.dorecomic.activity.ComicDetailActivity
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.Comic
import com.example.dorecomic.model.database.ComicDAO
import com.example.dorecomic.model.database.History
import com.example.dorecomic.utilities.COMIC_PATH

class HistoryAdapter(val context: Context, private val listHis: ArrayList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    private lateinit var rootView: View
    private lateinit var dao: ComicDAO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_grid_comic, parent, false)
        dao = AppDatabase.getInstance(context.applicationContext).comicDAO()
        return HistoryHolder(rootView)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val comic = getComicInHis(position)

        holder.comicName.text = comic.name

        Glide
            .with(context)
            .load(comic.cover)
            .fitCenter()
            .into(holder.comicImg)
    }

    override fun getItemCount(): Int {
        return listHis.size
    }

    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val comicName: TextView = itemView.findViewById(R.id.txt_name_vol_grid)
        val comicImg: ImageView = itemView.findViewById(R.id.cover_image)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, ComicDetailActivity::class.java).apply {
                    putExtra(COMIC_PATH, getComicInHis(absoluteAdapterPosition).path)
                }
                val option = ActivityOptions
                    .makeSceneTransitionAnimation(
                        context as Activity?,
                        Pair.create(comicImg, "comic_img_trans"),
                    )
                context.startActivity(intent, option.toBundle())
            }

        }
    }

    private fun getComicInHis(pos: Int): Comic {
        val page = dao.getPage(listHis[pos].pagePath)
        val chapter = dao.getChapter(page.chapterPath)
        return dao.getComic(chapter.comicPath)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateListData(ls: List<History>){
        listHis.clear()
        listHis.addAll(ls)
        notifyDataSetChanged()
    }
}