package com.newsportal.info_6134capstoneproject.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.newsportal.info_6134capstoneproject.model.Category
import com.newsportal.info_6134capstoneproject.ui.fragments.tabcontentfragment.TabContentFragment

class TabPageAdapter(fm: FragmentManager, private val categories: List<Category>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // Use newInstance with category name as an argument for each fragment
        return TabContentFragment.newInstance(categories[position].category)
    }

    override fun getCount(): Int {
        return categories.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categories[position].category
    }
}

