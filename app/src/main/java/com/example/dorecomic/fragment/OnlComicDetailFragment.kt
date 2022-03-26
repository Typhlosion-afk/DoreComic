package com.example.dorecomic.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.adapter.OnlListChapterAdapter
import com.example.dorecomic.model.database.Chapter
import com.example.dorecomic.model.database.Comic
import com.example.dorecomic.utilities.COMIC_PATH
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OnlComicDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnlComicDetailFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var mComic: Comic
    private var mListChapter = ArrayList<Chapter>()

    private lateinit var comicRef: StorageReference
    private lateinit var onlListChapterAdapter: OnlListChapterAdapter

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_detail_comic, container, false)

//        initData()
//        initView()
//        updateListChapter()

        return rootView
    }

    private fun initData() {
        val comicPath: String = arguments?.getString(COMIC_PATH) ?: ""
        Log.d("", "initData: $comicPath")
        comicRef = Firebase.storage.reference.child(comicPath)
        mComic = Comic(comicRef.path, comicRef.name, "${comicRef.path}/cover/cover.jpg")

    }

    private fun initView() {
        val coverView = rootView.findViewById<ImageView>(R.id.cover_image_detail)
        val comicName = rootView.findViewById<TextView>(R.id.comic_name_detail)
        val coverRef = Firebase.storage.reference.child(mComic.cover)
        comicName.text = mComic.name

        Glide
            .with(rootView.context)
            .load(coverRef)
            .fitCenter()
            .into(coverView)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.list_chapter_recycler)
        onlListChapterAdapter = OnlListChapterAdapter(mListChapter, rootView.context)
        LinearLayoutManager(rootView.context).apply {
            recyclerView.layoutManager = this
        }
        recyclerView.adapter = onlListChapterAdapter
    }

    private fun updateListChapter() = CoroutineScope(Dispatchers.IO).launch{
        comicRef.listAll().addOnSuccessListener {
            for (pref in it.prefixes) {
                if (pref.name != "cover") {
                    val chap = Chapter(pref.path, mComic.path, pref.name)
                    onlListChapterAdapter.addNewChapter(chap)
                    Log.d("TAG", "updateListChapter: ${chap.name}")
                }
            }
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OnlComicDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnlComicDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}