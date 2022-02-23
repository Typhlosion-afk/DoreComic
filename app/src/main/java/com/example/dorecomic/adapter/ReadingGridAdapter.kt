package com.example.dorecomic.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.database.Page

class ReadingGridAdapter(var context: Context, var ls: List<Page>) :
    RecyclerView.Adapter<ReadingGridAdapter.GridHolder>() {

    private lateinit var rootView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cardview_grid_reading, parent, false)
        return GridHolder(rootView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        Glide
            .with(context)
            .load(ls[position].path)
            .fitCenter()
            .into(holder.pageView)
        holder.txtPage.text = "${position + 1}"
    }

    override fun getItemCount(): Int {
        return ls.size
    }

    inner class GridHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pageView: ImageView = itemView.findViewById(R.id.cover_image)
        val txtPage: TextView = itemView.findViewById(R.id.txt_page)

        init {
            itemView.setOnClickListener {

            }
        }
    }
}