package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.dorecomic.R
import com.example.dorecomic.fragment.reading.ReadingFragment
import com.example.dorecomic.fragment.reading.ReadingGridFragment
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.model.database.Page
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.utilities.CHAPTER_PATH

class ReadingActivity : AppCompatActivity() {

    private lateinit var listPage: ArrayList<Page>
    private lateinit var message: String
    private lateinit var chapter: Chapter

    private lateinit var txtChapName: TextView
    private lateinit var btnPre: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowList: Button
    private lateinit var readingFragment: ReadingFragment
    private lateinit var readingGridFragment: ReadingGridFragment

    private var readingPage = 0;

    private var isReading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        initData()
        initView()
        initAction()
    }

    private fun initData() {
        listPage = ArrayList()
        val dao = AppDatabase.getInstance(applicationContext).comicDAO()
        val chapterPath: String = intent.getStringExtra(CHAPTER_PATH) ?: ""
        if (chapterPath != "") {
            chapter = dao.getChapter(chapterPath)
            listPage.addAll(dao.getListPageOf(chapterPath))

        }
    }

    private fun initView() {
        txtChapName = findViewById(R.id.chapter_name)
        txtChapName.text = chapter.name

        btnNext = findViewById(R.id.btn_next)
        btnPre = findViewById(R.id.btn_pre)
        btnShowList = findViewById(R.id.btn_continue)

        readingFragment = ReadingFragment()
        readingGridFragment = ReadingGridFragment()

        val listPageBundle = Bundle()
        listPageBundle.putSerializable("list_page", listPage)
        readingFragment.arguments = listPageBundle
        readingGridFragment.arguments = listPageBundle

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
        val readingPageBundle = Bundle()
        readingPageBundle.putInt("page", readingPage)

        isReading = if (!isReading) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, readingFragment, null)
                .commit()
            true
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, readingGridFragment, null)
                .commit()
            false
        }
    }

}