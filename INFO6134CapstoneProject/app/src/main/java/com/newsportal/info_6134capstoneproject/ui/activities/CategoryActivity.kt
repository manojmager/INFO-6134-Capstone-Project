package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.adapters.CategoryAdapter
import com.newsportal.info_6134capstoneproject.model.Category
import com.newsportal.info_6134capstoneproject.pref.NewsCategoryPrefs
import com.newsportal.info_6134capstoneproject.MainActivity

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
        recyclerView = findViewById(R.id.rvCategories)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val categories = listOf(
            Category(id = 1, category = "news"),
            Category(id = 2, category = "sport"),
            Category(id = 3, category = "tech"),
            Category(id = 4, category = "world"),
            Category(id = 5, category = "finance"),
            Category(id = 6, category = "politics"),
            Category(id = 7, category = "business"),
            Category(id = 8, category = "economics"),
            Category(id = 9, category = "entertainment"),
            Category(id = 10, category = "beauty"),
            Category(id = 11, category = "travel"),
            Category(id = 12, category = "music"),
            Category(id = 13, category = "food"),
            Category(id = 14, category = "science"),
            Category(id = 15, category = "gaming"),
            Category(id = 16, category = "energy"),
        )

        // Set up RecyclerView adapter
        categoryAdapter = CategoryAdapter(this, categories) { category ->
            // Toggle category selection
            val isSelected = newsCategoryPrefs.getCategoryList()?.contains(category) == true
            updateCategorySelection(category, !isSelected)
            categoryAdapter.notifyDataSetChanged()
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
