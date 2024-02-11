package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.CategoryAdapter
import com.newsportal.info_6134capstoneproject.model.Category
import com.newsportal.info_6134capstoneproject.pref.NewsCategoryPrefs

class CategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newsCategoryPrefs: NewsCategoryPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // Initialize SharedPreferences
        newsCategoryPrefs = NewsCategoryPrefs(this)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample Arraylist of categories
        val categories = listOf(
            Category(id = 1, category = "All"),
            Category(id = 2, category = "News"),
            Category(id = 3, category = "Sport"),
            Category(id = 4, category = "Tech"),
            Category(id = 6, category = "World"),
            Category(id = 7, category = "Finance"),
            Category(id = 8, category = "Politics"),
            Category(id = 9, category = "Economics"),
            Category(id = 10, category = "Entertainment"),
            Category(id = 11, category = "Beauty"),
            Category(id = 12, category = "Travel"),
            Category(id = 13, category = "Music"),
            Category(id = 14, category = "Food"),
            Category(id = 15, category = "Science"),
            Category(id = 16, category = "Gaming"),
            Category(id = 17, category = "Energy"),
        )

        // Set up RecyclerView adapter
        categoryAdapter = CategoryAdapter(categories) { category, isChecked ->
            // Update category selection status in SharedPreferences
            updateCategorySelection(category, isChecked)
        }
        recyclerView.adapter = categoryAdapter

        // Set click listener for the "To Dashboard" button
        val toDashButton: Button = findViewById(R.id.toDash)
        toDashButton.setOnClickListener {
            // Navigate to the dashboard activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun updateCategorySelection(category: Category, isChecked: Boolean) {
        // Update category selection status in SharedPreferences
        val selectedCategories = newsCategoryPrefs.getCategoryList()?.toMutableList() ?: mutableListOf()
        if (isChecked) {
            selectedCategories.add(category)
        } else {
            selectedCategories.remove(category)
        }
        newsCategoryPrefs.setCategoryList(selectedCategories)
    }
}