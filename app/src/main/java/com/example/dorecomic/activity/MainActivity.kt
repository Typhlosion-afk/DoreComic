package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.dorecomic.R
import androidx.viewpager2.widget.ViewPager2
import com.example.dorecomic.adapter.HomePagerAdapter
import com.example.dorecomic.fragment.FavoriteFragment
import com.example.dorecomic.fragment.HomeFragment
import com.example.dorecomic.fragment.UserFragment
import com.example.dorecomic.fragment.dialog.CloseAppDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var navBottom: BottomNavigationView
    private lateinit var pagerAdapter: HomePagerAdapter

    private val isLogin = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)
        navBottom = findViewById(R.id.nav_bottom_end)

        val listFragment: ArrayList<Fragment> = ArrayList<Fragment>()

        val favoriteFragment = FavoriteFragment()
        val userFragment = UserFragment()
        val homeFragment = HomeFragment()

        listFragment.add(homeFragment)
        listFragment.add(favoriteFragment)
        listFragment.add(userFragment)

        pagerAdapter = HomePagerAdapter(supportFragmentManager, listFragment)
        viewPager.adapter = pagerAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> navBottom.menu.findItem(R.id.home).isChecked = true
                    1 -> {
                        navBottom.menu.findItem(R.id.favor).isChecked = true
                        Toast.makeText(
                            this@MainActivity,
                            "Bạn đang sử dụng phiên bản Demo\nVui lòng chọn Manga Pokemon để test các tính năng",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    2 -> navBottom.menu.findItem(R.id.user).isChecked = true
                }
            }
        })

        navBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.favor -> viewPager.currentItem = 1
                R.id.user -> viewPager.currentItem = 2
            }
            true
        }
        viewPager.offscreenPageLimit = 3
    }

    override fun onBackPressed() {
        CloseAppDialogFragment().show(supportFragmentManager, null)
    }
}