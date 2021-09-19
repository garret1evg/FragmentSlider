package com.example.fragmentslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : FragmentActivity() {
    private var isDeletedLastFragment = false
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction().add(R.id.container,
//                BlankFragment.newInstance()).commit()
//        }
        adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter
        val callback = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE && isDeletedLastFragment){
                    adapter.notifyDataSetChanged()
                    isDeletedLastFragment = false
                }
            }
        }
        viewPager.registerOnPageChangeCallback(callback)
    }
    fun addNewFragment(){
        adapter.fragmentsCount++
        adapter.notifyDataSetChanged()
        viewPager.currentItem = adapter.fragmentsCount
    }
    fun deleteFragment(num: Int){
        viewPager.currentItem = num - 2
        adapter.fragmentsCount = num - 1
        isDeletedLastFragment = true
    }
}
