package com.example.dorecomic.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListComicAdapter
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.Comic
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var isGridView: Boolean = true
    private lateinit var recyclerView: RecyclerView
    private lateinit var rootView: View

    // TODO: Rename and change types of parameters
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
        rootView = inflater.inflate(R.layout.fragment_offline, container, false)

        initData()
        initView()
        initAdapter()

        return rootView
    }

    private fun initData() {

    }

    private fun initView() {
        recyclerView = rootView.findViewById(R.id.list_comic_content)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initAdapter() {
//        rootDir = File(rootPath)
//        val ls : ArrayList<Comic> = ArrayList()
//        ls.clear()
//        for(f:File in rootDir.listFiles()!!){
//            val coverPath = "${f.absolutePath}/cover/cover.jpg"
//            ls.add(Comic(0,f.absolutePath ,f.name, coverPath))
//        }
//        context?.let {
//            AppDatabase
//            .getInstance(it)
//            .comicDAO()
//                .addListComic(ls)}

        val ls: ArrayList<Comic> = ArrayList()
        ls.clear()
        context?.let { AppDatabase.getInstance(it).comicDAO().getListComic() }
            ?.let { ls.addAll(it) }

        if (isGridView) {
            GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
                .apply {
                    recyclerView.layoutManager = this
                }
        }
        recyclerView.adapter = context?.let { ListComicAdapter(ls, it) }
    }
}

