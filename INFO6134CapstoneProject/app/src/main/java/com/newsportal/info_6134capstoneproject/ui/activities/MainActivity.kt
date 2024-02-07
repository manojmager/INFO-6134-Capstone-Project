package com.newsportal.info_6134capstoneproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.newsportal.info_6134capstoneproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newsportal.info_6134capstoneproject.ui.fragments.BookmarkFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.HomeFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SearchFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val dummyJson = """
            {
              "tabs": [
                {"id": 1, "title": "All"},
                {"id": 2, "title": "World"},
                {"id": 3, "title": "Sports"},
                {"id": 4, "title": "Politics"},
                {"id": 5, "title": "Game"}
              ]
            }
        """

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val homeFragment= HomeFragment()
        val bookmarkFragment= BookmarkFragment()
        val searchFragment= SearchFragment()
        val settingsFragment= SettingsFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->setCurrentFragment(homeFragment)
                R.id.bookmark ->setCurrentFragment(bookmarkFragment)
                R.id.search ->setCurrentFragment(searchFragment)
                R.id.settings ->setCurrentFragment(settingsFragment)
            }
            true
        }

        val bundle = Bundle().apply {
            putString("jsonData", dummyJson)
        }

        val fragment = HomeFragment.newInstance("")
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, fragment)
            .commit()
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()


    }
    override fun onPause() {
        super.onPause()
    }
    override fun onStop() {
        super.onStop()
    }
    override fun onRestart() {
        super.onRestart()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}