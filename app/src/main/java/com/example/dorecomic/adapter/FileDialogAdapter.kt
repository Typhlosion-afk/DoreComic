package com.example.dorecomic.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import java.io.File

class FileDialogAdapter(val context: Context, var mListFile: ArrayList<File>) :
    RecyclerView.Adapter<FileDialogAdapter.FileHolder>() {

    private lateinit var rootView: View

    private lateinit var selectedPath: String

    private var selectedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FileDialogAdapter.FileHolder {
        rootView = LayoutInflater.from(context).inflate(R.layout.card_file_chooser, parent, false)
        return FileHolder(rootView)
    }

    override fun onBindViewHolder(holder: FileDialogAdapter.FileHolder, position: Int) {
        holder.checkBox.tag = position
        holder.checkBox.isChecked = position == selectedPosition
        holder.txtName.text = mListFile[position].name

    }

    override fun getItemCount(): Int = mListFile.size

    @SuppressLint("NotifyDataSetChanged")
    inner class FileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById<TextView>(R.id.txt_file_name)
        var checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.checkbox_file)

        init {
            itemView.setOnClickListener {
                selectedPath = mListFile[absoluteAdapterPosition].absolutePath
                selectedPosition = absoluteAdapterPosition
                notifyDataSetChanged()
            }
        }
    }

    fun getSelectedPath() = selectedPath

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: ArrayList<File>) {
        mListFile = newList
        selectedPosition = -1

        this.notifyDataSetChanged()
    }
}