package com.newsportal.info_6134capstoneproject.ui.activities

import HomeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.newsportal.info_6134capstoneproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newsportal.info_6134capstoneproject.databinding.ActivityMainBinding
import com.newsportal.info_6134capstoneproject.ui.fragments.BookmarkFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SearchFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        initBottomNavigation()
    }
    private fun initVars() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }
    private fun initBottomNavigation() {
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