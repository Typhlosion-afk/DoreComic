package com.example.dorecomic.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ReadingAdapter
import com.example.dorecomic.model.database.AppDatabase
import com.example.dorecomic.model.database.History
import com.example.dorecomic.model.database.Page

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadingGridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: ReadingAdapter
    private var listPage = ArrayList<Page>()
//    private var readingPage = 0;

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
        rootView = inflater.inflate(R.layout.fragment_reading, container, false)

        initData()
        initView()

        return rootView
    }

    private fun initData() {
        listPage.addAll(arguments?.getSerializable("list_page") as List<Page>)
//        readingPage = arguments?.getInt("page") ?: 0
    }

    private fun initView() {

        recyclerView = rootView.findViewById(R.id.reading_slide_container)
        mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            .apply {
                recyclerView.layoutManager = this
            }
        mAdapter = ReadingAdapter(rootView.context, listPage)
        recyclerView.adapter = mAdapter
//        recyclerView.scrollToPosition(readingPage)

    }

    override fun onPause() {
        super.onPause()
//        val scrollPosition = mLinearLayoutManager.findLastVisibleItemPosition()
//        AppDatabase
//            .getInstance(rootView.context)
//            .comicDAO()
//            .addHis(History(0, 1645643279314, listPage[scrollPosition].path))
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReadingGridFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadingGridFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}