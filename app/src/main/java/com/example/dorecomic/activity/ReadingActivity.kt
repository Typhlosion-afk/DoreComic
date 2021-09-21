package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dorecomic.R
import kotlinx.android.synthetic.main.activity_reading.*

class ReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val message = intent.getStringExtra("vol")
        this.test_text.text = message.toString()
    }
}