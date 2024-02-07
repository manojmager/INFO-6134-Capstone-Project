package com.newsportal.info_6134capstoneproject.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.newsportal.info_6134capstoneproject.adapters.TabPageAdapter
import com.newsportal.info_6134capstoneproject.R
import org.json.JSONObject
class HomeFragment : Fragment() {

    private var jsonData: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the jsonData from arguments
        jsonData = arguments?.getString("jsonData")

        Log.d(TAG, "HomeFragment onViewCreated: + $jsonData")

        // Parse the JSON data and set up tabs
        setupTabs(jsonData)
    }

    private fun setupTabs(jsonData: String?) {
        try {
            val jsonObject = JSONObject(jsonData)
            val tabsArray = jsonObject.getJSONArray("tabs")

            val tabLayout = view?.findViewById<TabLayout>(R.id.tabLayout)

            for (i in 0 until tabsArray.length()) {
                val tabObject = tabsArray.getJSONObject(i)
                val title = tabObject.getString("title")

                val tab = tabLayout?.newTab()
                tab?.text = title
                tabLayout?.addTab(tab!!)
            }

            // Set up ViewPager with the TabLayout
            val viewPager = view?.findViewById<ViewPager>(R.id.viewPager)
            val adapter = TabPageAdapter(childFragmentManager, tabsArray)
            viewPager?.adapter = adapter

            // Connect the TabLayout and ViewPager
            viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { viewPager?.currentItem = it.position }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        fun newInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            // You can pass any arguments to the fragment here if needed
            val args = Bundle().apply {
                putString("title", title)
            }
            fragment.arguments = args
            return fragment
        }
    }
}