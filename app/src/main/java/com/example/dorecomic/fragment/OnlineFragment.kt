package com.example.dorecomic.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListComicAdapter
import com.example.dorecomic.adapter.OnlineComicAdapter
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.Comic
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.ref.Reference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View

    private var isGridView: Boolean = true
    private lateinit var recyclerView: RecyclerView
    private lateinit var mListOnlComic: ArrayList<Comic>
    private lateinit var database: DatabaseReference
    private lateinit var onlAdapter: OnlineComicAdapter

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
        rootView = inflater.inflate(R.layout.fragment_online, container, false)
        mListOnlComic = ArrayList()

//        initAdapter()
//        initData()
        return rootView
    }

    private fun initData() = CoroutineScope(Dispatchers.IO).launch {
        val storage = Firebase.storage
        val rootRef = storage.reference
        rootRef.listAll()
            .addOnSuccessListener {
                for (prefix in it.prefixes) {
                    val comic = Comic(
                        prefix.path,
                        prefix.name,
                        "${prefix.path}/cover/cover.jpg")
//                    mListOnlComic.add(comic)
                    onlAdapter.addNewComic(comic)
                }
            }
    }


    private fun initAdapter() {
        recyclerView = rootView.findViewById(R.id.onl_comic_container)
        Log.d("TAG", "initAdapter: ${mListOnlComic.size}")
        if (isGridView) {
            GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
                .apply {
                    recyclerView.layoutManager = this
                }
        }
        onlAdapter = OnlineComicAdapter(rootView.context, mListOnlComic)
        recyclerView.adapter = onlAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}