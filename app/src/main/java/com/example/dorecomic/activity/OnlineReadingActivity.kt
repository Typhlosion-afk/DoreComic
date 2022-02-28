package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dorecomic.R
import com.example.dorecomic.fragment.OnlReadingFragment
import com.example.dorecomic.fragment.ReadingFragment
import com.example.dorecomic.fragment.ReadingGridFragment
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.model.database.ComicDAO
import com.example.dorecomic.model.database.Page
import com.example.dorecomic.utilities.CHAPTER_PATH
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class OnlineReadingActivity : AppCompatActivity() {

    private lateinit var chapter: Chapter

    private lateinit var txtChapName: TextView
    private lateinit var btnPre: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowList: Button
    private lateinit var readingFragment: OnlReadingFragment
    private lateinit var dao: ComicDAO

    private lateinit var chapterPath: String
    private lateinit var chapterRef: StorageReference

    private var pos: Int = 0
    private var listChapter = ArrayList<Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        initData()
        initView()
        initAction()
    }

    private fun initData() {
        chapterPath = intent.getStringExtra(CHAPTER_PATH) ?: ""
        chapterRef = Firebase.storage.reference.child(chapterPath)
        chapter = Chapter(chapterRef.path, chapterRef.parent?.path ?: "", chapterRef.name)
        pos = intent.getIntExtra("pos", 0)

         listChapter.addAll(intent.getSerializableExtra("list_chapter") as List<Chapter>)

    }


    private fun initView() {

        txtChapName = findViewById(R.id.chapter_name)
        txtChapName.text = chapter.name

        btnNext = findViewById(R.id.btn_next)
        btnPre = findViewById(R.id.btn_pre)
//        btnShowList = findViewById(R.id.btn_continue)

        val readingFragment = OnlReadingFragment()

        val listPageBundle = Bundle()
        listPageBundle.putString(CHAPTER_PATH, chapterPath)

        readingFragment.arguments = listPageBundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, readingFragment, null)
            .commit()
    }

    private fun initAction() {

        btnNext.setOnClickListener {
            if (pos < listChapter.size - 1){
                pos += 1
                val readingFragment = OnlReadingFragment()

                Log.d("POS", "initAction: $pos")

                chapterPath = listChapter[pos].path
                txtChapName.text = listChapter[pos].name

                val listPageBundle = Bundle()
                listPageBundle.putString(CHAPTER_PATH, chapterPath)
                readingFragment.arguments = listPageBundle

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, readingFragment, null)
                    .commit()

            } else {
                Toast.makeText(
                    this@OnlineReadingActivity,
                    "Bạn đang đọc chap cuối cùng",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        btnPre.setOnClickListener {
            if (pos > 0) {
                pos -= 1
                Log.d("POS", "initAction: $pos")
                val readingFragment = OnlReadingFragment()

                chapterPath = listChapter[pos].path
                txtChapName.text = listChapter[pos].name
                val listPageBundle = Bundle()
                listPageBundle.putString(CHAPTER_PATH, chapterPath)
                readingFragment.arguments = listPageBundle

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, readingFragment, null)
                    .commit()

            } else {
                Toast.makeText(
                    this@OnlineReadingActivity,
                    "Bạn đang ở chap đầu tiên",
                    Toast.LENGTH_LONG
                ).show()
            }

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