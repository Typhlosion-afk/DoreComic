package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.dorecomic.R
import com.example.dorecomic.fragment.OnlReadingFragment
import com.example.dorecomic.fragment.ReadingFragment
import com.example.dorecomic.fragment.ReadingGridFragment
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.model.database.Page
import com.example.dorecomic.utilities.CHAPTER_PATH
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class OnlineReadingActivity : AppCompatActivity() {

    private lateinit var chapter: Chapter

    private lateinit var txtChapName: TextView
    private lateinit var btnPre: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowList: Button
    private lateinit var readingFragment: OnlReadingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        initView()
    }

    private fun initView() {
        val chapterPath: String = intent.getStringExtra(CHAPTER_PATH) ?: ""
        val chapterRef = Firebase.storage.reference.child(chapterPath)
        chapter = Chapter(chapterRef.path, chapterRef.parent?.path ?: "", chapterRef.name)

        txtChapName = findViewById(R.id.chapter_name)
        txtChapName.text = chapter.name

        btnNext = findViewById(R.id.btn_next)
        btnPre = findViewById(R.id.btn_pre)
        btnShowList = findViewById(R.id.btn_continue)

        readingFragment = OnlReadingFragment()

        val listPageBundle = Bundle()
        listPageBundle.putString(CHAPTER_PATH, chapterPath)
        readingFragment.arguments = listPageBundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, readingFragment, null)
            .commit()
    }

    private fun initAction() {
        btnShowList.setOnClickListener {
            replaceFragment()
        }
    }

    private fun replaceFragment() {
//        val readingPageBundle = Bundle()
//        readingPageBundle.putInt("page", readingPage)
//
//        isReading = if (!isReading) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.container, readingFragment, null)
//                .commit()
//            true
//        } else {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.container, readingGridFragment, null)
//                .commit()
//            false
//        }
    }

}