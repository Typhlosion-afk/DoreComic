package com.example.dorecomic.fragment.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.dorecomic.R
import com.example.dorecomic.adapter.BannerPagerAdapter
import com.example.dorecomic.adapter.HistoryAdapter
import com.example.dorecomic.model.Banner
import com.example.dorecomic.model.Comic
import me.relex.circleindicator.CircleIndicator
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewPager: ViewPager
    private lateinit var rootView: View

    private lateinit var indicator: CircleIndicator
    private var handler = Handler()
    private var bannerSize = 0
    private lateinit var swipeTimer: Timer
    private var isRunning = false

    private lateinit var rootDir: File
    private var rootPath: String = "/storage/6431-3633/.comic/"


    private lateinit var recyclerView: RecyclerView
    private val listHis = ArrayList<Comic>()

    private var runnable = Runnable {
        if(viewPager.currentItem == listBanner.size - 1){
            viewPager.currentItem = 0
        }else {
            val i = viewPager.currentItem
            viewPager.currentItem = i + 1
        }
    }

    private var listBanner = ArrayList<Banner>()

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
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false)
        initData()
        initView()
        initAction()

        return rootView
    }

    private fun initView(){
        indicator = rootView.findViewById(R.id.banner_indicator)
        viewPager = rootView.findViewById(R.id.banner_view_pager)

        val bannerPagerAdapter = BannerPagerAdapter(listBanner)
        viewPager.adapter = bannerPagerAdapter
        indicator.setViewPager(viewPager)

        recyclerView = rootView.findViewById(R.id.list_history)
    }

    private fun setTimer(){
        isRunning = true
        swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(runnable)
            }
        }, 3000, 3000)
    }

    private fun initAction(){
        setTimer()
        val historyAdapter = activity?.let { HistoryAdapter(it, listHis) }
        LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            .apply {
                recyclerView.layoutManager = this
            }
        recyclerView.adapter = historyAdapter
    }

    override fun onPause() {
        super.onPause()
        if(isRunning){
            swipeTimer.cancel()
            handler.removeCallbacks(runnable)
            isRunning = false
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isRunning){
            setTimer()
        }
    }

    private fun initData(){
        listBanner.add(Banner(BitmapFactory.decodeResource(resources,R.drawable.banner_1)))
        listBanner.add(Banner(BitmapFactory.decodeResource(resources,R.drawable.banner_2)))
        listBanner.add(Banner(BitmapFactory.decodeResource(resources,R.drawable.banner_3)))
        listBanner.add(Banner(BitmapFactory.decodeResource(resources,R.drawable.banner_4)))


        rootDir = File(rootPath)
        val ls : ArrayList<Comic> = ArrayList<Comic>()
        ls.clear()
        for(f: File in rootDir.listFiles()!!){
            val coverPath = "${f.absolutePath}/cover/cover.jpg"
            listHis.add(Comic(f.absolutePath, f.name, coverPath))
        }
        for(f: File in rootDir.listFiles()!!){
            val coverPath = "${f.absolutePath}/cover/cover.jpg"
            listHis.add(Comic(f.absolutePath, f.name, coverPath))
        }
        for(f: File in rootDir.listFiles()!!){
            val coverPath = "${f.absolutePath}/cover/cover.jpg"
            listHis.add(Comic(f.absolutePath, f.name, coverPath))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}