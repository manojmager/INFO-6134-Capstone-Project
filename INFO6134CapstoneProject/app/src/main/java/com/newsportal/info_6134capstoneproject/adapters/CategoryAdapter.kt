package com.newsportal.info_6134capstoneproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
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

        // Set the appropriate image based on the category name
        when (category.category) {
            "news" -> holder.imageViewCategory.setImageResource(R.drawable.news)
            "sport" -> holder.imageViewCategory.setImageResource(R.drawable.sports)
            "tech" -> holder.imageViewCategory.setImageResource(R.drawable.tech)
            "world" -> holder.imageViewCategory.setImageResource(R.drawable.world)
            "finance" -> holder.imageViewCategory.setImageResource(R.drawable.finance)
            "politics" -> holder.imageViewCategory.setImageResource(R.drawable.politics)
            "economics" -> holder.imageViewCategory.setImageResource(R.drawable.economics)
            "entertainment" -> holder.imageViewCategory.setImageResource(R.drawable.tech)
            "beauty" -> holder.imageViewCategory.setImageResource(R.drawable.beauty)
            "travel" -> holder.imageViewCategory.setImageResource(R.drawable.travel)
            "music" -> holder.imageViewCategory.setImageResource(R.drawable.music)

//            "food" -> holder.imageViewCategory.setImageResource(R.drawable.food)
//            "science" -> holder.imageViewCategory.setImageResource(R.drawable.science)
//            "energy" -> holder.imageViewCategory.setImageResource(R.drawable.energy)
//            "gaming" -> holder.imageViewCategory.setImageResource(R.drawable.gaming)
            else -> holder.imageViewCategory.setImageResource(R.drawable.ic_launcher_foreground)
        }

        // Check if the category is selected
        val isSelected = newsCategoryPrefs.getCategoryList()?.contains(category) == true

        // Set selected state of the CardView
        if (isSelected) {
            holder.cardViewCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.my_app_primary))
            // You can also apply a different foreground effect or customize as needed
        } else {
            holder.cardViewCategory.setCardBackgroundColor(ContextCompat.getColor(context, R.color.my_app_accent))
            // Reset to the default background color when not selected
        }

        holder.cardViewCategory.setOnClickListener {
            onCategoryClickListener(category)
        }
    }

    override fun getItemCount(): Int = categories.size
}