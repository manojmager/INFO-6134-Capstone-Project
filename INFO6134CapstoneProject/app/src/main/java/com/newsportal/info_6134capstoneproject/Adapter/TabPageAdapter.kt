package com.newsportal.info_6134capstoneproject.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.newsportal.info_6134capstoneproject.ui.fragments.HomeFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.TabContentFragment
import org.json.JSONArray

class TabPageAdapter(fm: FragmentManager, private val tabsArray: JSONArray) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // Use newInstance with title as an argument for each fragment
        return TabContentFragment.newInstance(tabsArray.getJSONObject(position).getString("title"))
    }

    override fun getCount(): Int {
        return tabsArray.length()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabsArray.getJSONObject(position).getString("title")
    }
}

