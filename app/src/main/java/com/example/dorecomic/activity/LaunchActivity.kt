package com.example.dorecomic.activity

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dorecomic.R
import com.example.dorecomic.fragment.dialog.RootFileChooserFragment
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.model.database.Comic
import com.example.dorecomic.model.database.Page
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.utilities.FILE_PERMISSION_CODE
import com.example.dorecomic.utilities.ROOT_FILE_KEY
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import java.io.File

class LaunchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        firebaseCheckApp()
        checkPermission()
    }

    private fun onPermissionGranted() {
        setRootFile()
    }

    private fun firebaseCheckApp() {
        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
    }

    private fun setRootFile() {
        val sharedPrefs: SharedPreferences = getPreferences(MODE_PRIVATE) ?: return
        val rootPath: String = sharedPrefs.getString(ROOT_FILE_KEY, "").toString()
        if (rootPath == "") {
            val rootFileChooserFragment = RootFileChooserFragment()
            rootFileChooserFragment.show(supportFragmentManager, null)
            supportFragmentManager
                .setFragmentResultListener("result", this@LaunchActivity) { requestKey, bundle ->
                    val data = bundle.getString("path")
                    Log.d("TAG", "setRootFile: $data")
                    setRootFile()
                }
        } else {
            initDataBase(rootPath)
        }
    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this@LaunchActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(
                this@LaunchActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this@LaunchActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                FILE_PERMISSION_CODE
            )
        } else {
            onPermissionGranted()
        }
    }

    private fun initDataBase(rootPath: String) {
        val rootFile = File("$rootPath/.Comic")
        val comicDAO = AppDatabase.getInstance(this).comicDAO()

        if (!rootFile.exists()) {
//            rootFile.mkdirs()
            Log.d("file", "initDataBase: can not found dir")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val lsComic: ArrayList<Comic> = ArrayList()
        val lsChapter: ArrayList<Chapter> = ArrayList()
        val lsPage: ArrayList<Page> = ArrayList()

        comicDAO.deleteComic()
        comicDAO.deletePage()
        comicDAO.deleteChapter()

        //add comic data
        for (comicFile: File in rootFile.listFiles() ?: return) {
            val coverPath = "${comicFile.absolutePath}/cover/cover.jpg"
            val comic = Comic(comicFile.absolutePath, comicFile.name, coverPath)
            lsComic.add(comic)
            //add chapter data
            for (chapterFile: File in File(comic.path).listFiles() ?: return) {
                val chapter = Chapter(chapterFile.absolutePath, comic.path, chapterFile.name)
                if (chapterFile.name != "cover") {
                    lsChapter.add(chapter)
                    //add page data
                    for (pageFile: File in File(chapter.path).listFiles() ?: return) {
                        var page = 0;
                        lsPage.add(Page(pageFile.absolutePath, chapter.path, ++page, pageFile.name))
                    }
                }
            }
        }
        comicDAO.addListComic(lsComic)
        comicDAO.addListChapter(lsChapter)
        comicDAO.addListPage(lsPage)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FILE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            }
        }
    }
}