package com.example.dorecomic.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.ListComicAdapter
import com.example.dorecomic.model.Comic

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
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_offline, container, false)

        initView()
        initAdapter()

        return rootView
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

    private fun initAdapter(){
        val ls : ArrayList<Comic> = ArrayList<Comic>()
        ls.add(Comic("Pokemon đặc biệt", null))
        ls.add(Comic("Slime chuyển sinh", null))
        ls.add(Comic("Thám tử Conan", null))
        ls.add(Comic("Mèo máy Doremon", null))
        ls.add(Comic("Kiếm sĩ Yaiba", null))
        if(isGridView){
            GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
                .apply {
                    recyclerView.layoutManager = this
            }
        }
        recyclerView.adapter = context?.let { ListComicAdapter(ls, it) }
    }
}