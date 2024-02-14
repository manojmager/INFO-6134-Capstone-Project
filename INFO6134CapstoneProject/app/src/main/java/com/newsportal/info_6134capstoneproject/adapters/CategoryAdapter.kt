package com.newsportal.info_6134capstoneproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Category

class CategoryAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onCategoryCheckedChangeListener: (Category, Boolean) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxCategory: CheckBox = itemView.findViewById(R.id.cbNews)
        val textViewCategoryName: TextView = itemView.findViewById(R.id.tvNewsCategory)
        val imageViewCategory: ImageView = itemView.findViewById(R.id.ivNewsCategory)
        val cardViewCategory: CardView = itemView.findViewById(R.id.cvNewsCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.textViewCategoryName.text = category.category
        // Set the appropriate image based on the category name or ID
        when (category.category) {
            "All" -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
            "Gaming" -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
            "Sport" -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
            "Tech" -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
            "Finance" -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)

            else -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.checkBoxCategory.isChecked = false // Reset state to ensure proper recycling of views

        holder.checkBoxCategory.setOnCheckedChangeListener { _, isChecked ->
            onCategoryCheckedChangeListener(category, isChecked)
        }

        holder.cardViewCategory.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = categories.size
}