package com.example.fragmentslider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    var fragmentsCount = 1
    override fun getItemCount(): Int {
        return fragmentsCount
    }

    override fun createFragment(position: Int): Fragment {
        return BlankFragment.newInstance(position + 1)
    }
}