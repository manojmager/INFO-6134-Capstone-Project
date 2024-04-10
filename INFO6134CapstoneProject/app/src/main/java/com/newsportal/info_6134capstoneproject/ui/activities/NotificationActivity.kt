package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.NotificationAdapter

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var clearSaved: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        clearSaved = findViewById(R.id.clearSaved)

        // Initialize RecyclerView
        notificationRecyclerView = findViewById(R.id.notificationRecyclerView)
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)

        val savedArticles = NotificationAdapter.getSavedArticlesFromSharedPreferences(this)
        val sortedArticles = savedArticles.sortedByDescending { it.published_date }

        notificationAdapter = NotificationAdapter(sortedArticles)
        notificationRecyclerView.adapter = notificationAdapter

        clearSaved.setOnClickListener {
            showConfirmationDialog()
        }

        setupToolbar()
    }

    private fun showConfirmationDialog() {
        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)

        val builder = if (isDarkMode) {
            AlertDialog.Builder(this, R.style.DarkTheme_AlertDialog)
        } else {
            AlertDialog.Builder(this, R.style.LightTheme_AlertDialog)
        }

        builder.setTitle("Clear Saved Data")
            .setMessage("Are you sure you want to clear all saved data?")
            .setPositiveButton("Yes") { dialog, _ ->
                clearSharedPreferences()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        notificationAdapter.updateArticles(emptyList())
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.notificationToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

