package com.newsportal.info_6134capstoneproject.pref
import android.content.Context
import android.content.SharedPreferences
import com.newsportal.info_6134capstoneproject.model.Category
import org.json.JSONArray
import org.json.JSONObject

class NewsCategoryPrefs(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)

    companion object {
        private const val APP_SHARED_PREFS = "NEWS_CATEGORY_PREF"
        private const val PREF_DATA = "category_list"
    }

    fun setCategoryList(categories: List<Category>) {
        val jsonArray = JSONArray()
        for (category in categories) {
            val categoryJson = JSONObject().apply {
                put("id", category.id)
                put("category", category.category)
            }
            jsonArray.put(categoryJson)
        }
        sharedPreferences.edit().putString(PREF_DATA, jsonArray.toString()).apply()
    }

    fun getCategoryList(): List<Category>? {
        val json = sharedPreferences.getString(PREF_DATA, null) ?: return null
        val jsonArray = JSONArray(json)
        val categories = mutableListOf<Category>()
        for (i in 0 until jsonArray.length()) {
            val categoryJson = jsonArray.getJSONObject(i)
            val id = categoryJson.getInt("id")
            val name = categoryJson.getString("category")
            // Create Category object and add it to the list
            val category = Category(id, name)
            categories.add(category)
        }
        return categories
    }

    fun clearCategoryList() {
        sharedPreferences.edit().remove(PREF_DATA).apply()
    }
}