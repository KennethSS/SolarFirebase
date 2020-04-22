package com.solar.firebase

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CategoryPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view
    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int): Fragment = fragments[position]
}