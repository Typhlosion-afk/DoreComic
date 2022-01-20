package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ReadingAdapter
import com.example.dorecomic.model.Page
import kotlinx.android.synthetic.main.activity_reading.*
import java.io.File

class ReadingActivity : AppCompatActivity() {

    private lateinit var listPage: ArrayList<Page>
    private lateinit var message: String
    private lateinit var chapterFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        initData()
        initRecycleView()
    }

    private fun initData() {
        listPage = ArrayList()
        message = intent.getStringExtra("vol") as String
        Log.d("TAG", "initData: $message")
        chapterFile = File(message)
        var num = 1
        for (f in chapterFile.listFiles()!!){
            if(f.extension == "jpg"){
                listPage.add(Page(num++,f.name, f.absolutePath))
            }
        }
        Log.d("TAG", "initData: ${listPage.size}")
    }

    private fun initRecycleView() {
        val recyclerView: RecyclerView = container

        LinearLayoutManager(this)
            .apply {
                recyclerView.layoutManager = this
        }
        recyclerView.isNestedScrollingEnabled = true
        recyclerView.adapter = ReadingAdapter(this, listPage)
    }
}