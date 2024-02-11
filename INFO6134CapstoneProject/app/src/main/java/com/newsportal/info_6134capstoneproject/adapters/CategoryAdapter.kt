package com.newsportal.info_6134capstoneproject.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryCheckedChangeListener: (Category, Boolean) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxCategory: CheckBox = itemView.findViewById(R.id.checkBoxCategory)
        val textViewCategoryName: TextView = itemView.findViewById(R.id.textViewCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.textViewCategoryName.text = category.category
        holder.checkBoxCategory.isChecked = false // Reset state to ensure proper recycling of views

        holder.checkBoxCategory.setOnCheckedChangeListener { _, isChecked ->
            onCategoryCheckedChangeListener(category, isChecked)
        }
    }

    override fun getItemCount(): Int = categories.size
}