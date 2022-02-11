package com.example.dorecomic.adapter

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.dorecomic.R
import com.example.dorecomic.model.Banner

class BannerPagerAdapter(private val listBanner: List<Banner>): PagerAdapter() {

    private lateinit var rootView: View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        rootView = LayoutInflater.from(container.context).inflate(R.layout.banner_item, container, false)
        val img = rootView.findViewById<ImageView>(R.id.item_banner_container)
        img.setImageBitmap(listBanner[position].resBanner)
        container.addView(rootView,0)

        return rootView
    }

    override fun getCount(): Int {
        return listBanner.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}