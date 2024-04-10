package com.newsportal.info_6134capstoneproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Category
import com.newsportal.info_6134capstoneproject.pref.NewsCategoryPrefs

class CategoryAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onCategoryClickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var newsCategoryPrefs: NewsCategoryPrefs

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCategoryName: TextView = itemView.findViewById(R.id.tvNewsCategory)
        val imageViewCategory: ImageView = itemView.findViewById(R.id.ivNewsCategory)
        val cardViewCategory: CardView = itemView.findViewById(R.id.cvNewsCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        newsCategoryPrefs = NewsCategoryPrefs(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.textViewCategoryName.text = category.category

        Glide.with(context)
            .load(getCategoryImage(category.category))
            .into(holder.imageViewCategory)

        val isSelected = newsCategoryPrefs.getCategoryList()?.contains(category) == true
        // Set selected state of the CardView
        if (isSelected) {
            holder.cardViewCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.my_app_primary))
        } else {
            holder.cardViewCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.my_app_accent))
        }

        holder.cardViewCategory.setOnClickListener {
            onCategoryClickListener(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    private fun getCategoryImage(categoryName: String): Int {
        return when (categoryName) {
            "news" -> R.drawable.news
            "sport" -> R.drawable.sports
            "tech" -> R.drawable.tech
            "world" -> R.drawable.world
            "finance" -> R.drawable.finance
            "politics" -> R.drawable.politics
            "economics" -> R.drawable.economics
            "entertainment" -> R.drawable.tech
            "beauty" -> R.drawable.beauty
            "travel" -> R.drawable.travel
            "music" -> R.drawable.music
            else -> R.drawable.ic_launcher_foreground
        }
    }
}
