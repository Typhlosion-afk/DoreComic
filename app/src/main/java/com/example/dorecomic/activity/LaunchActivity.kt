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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.dorecomic.R
import com.example.dorecomic.fragment.dialog.RootFileChooserFragment
import com.example.dorecomic.model.database.*
import com.example.dorecomic.utilities.FILE_PERMISSION_CODE
import com.example.dorecomic.utilities.ROOT_FILE_KEY
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

@DelicateCoroutinesApi
class LaunchActivity : AppCompatActivity(){

    private lateinit var dao: ComicDAO
    private var rootPath: String = ""
    private lateinit var rootFile: File

    private val lsComic: ArrayList<Comic> = ArrayList()
    private val lsChapter: ArrayList<Chapter> = ArrayList()
    private val lsPage: ArrayList<Page> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_launch)
//        splashScreen.setKeepOnScreenCondition { true }

//        firebaseCheckApp()
        checkPermission()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun onPermissionGranted() {
        setRootFile()
    }

    private fun firebaseCheckApp() = runBlocking{
        val initializer = async { FirebaseApp.initializeApp(/*context=*/this@LaunchActivity) }
        initializer.await()
        val firebaseAppCheck =  FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
    }

    private fun setRootFile() {
        val sharedPrefs: SharedPreferences = getPreferences(MODE_PRIVATE) ?: return
        rootPath = sharedPrefs.getString(ROOT_FILE_KEY, "").toString()
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
            initDataBase()
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


    private fun initDataBase() {
        rootFile = File("$rootPath/.Comic")
        dao = AppDatabase.getInstance(this).comicDAO()

        if (!rootFile.exists()) {
//            rootFile.mkdirs()
            Log.d("file", "initDataBase: can not found dir")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        Log.d("Check time coroutine", "initDataBase: begin")
        runBlocking {
            val readJob = GlobalScope.launch(Dispatchers.IO) {
                Log.d("Check time coroutine", "initDataBase: read")
                deleteDataBase()
                Log.d("Check time coroutine", "initDataBase: read done")

            }
            val deleteJob = GlobalScope.launch(Dispatchers.Default) {
                Log.d("Check time coroutine", "initDataBase: delete")
                readFromStorage()
                Log.d("Check time coroutine", "initDataBase: delete done")
            }

            val checkAppJob = GlobalScope.launch(Dispatchers.Default) {
                firebaseCheckApp()
            }

            joinAll(readJob, deleteJob)
            Log.d("Check time coroutine", "initDataBase: add")
            insertDataBase()
            Log.d("Check time coroutine", "initDataBase: add done")
            checkAppJob.join()
            startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
            finish()
        }
    }

    //read and model data from storage
    private fun readFromStorage() {
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
    }

    //delete data in table
    private fun deleteDataBase() {
        dao.deleteComic()
        dao.deleteChapter()
        dao.deletePage()
    }

    //insert new data to table
    private fun insertDataBase() {
        dao.addListComic(lsComic)
        dao.addListChapter(lsChapter)
        dao.addListPage(lsPage)
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

    override fun onDestroy() {
        super.onDestroy()
    }
}