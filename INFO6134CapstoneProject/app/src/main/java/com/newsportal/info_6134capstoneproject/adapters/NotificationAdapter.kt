package com.newsportal.info_6134capstoneproject.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.ui.activities.ArticleDetailsActivity

class NotificationAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.notificationTitle)
        val messageTextView: TextView = itemView.findViewById(R.id.notificationSummary)
        val notificationImage: ImageView = itemView.findViewById(R.id.notificationImage)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val title = articles[position].title ?: ""
                    val imageUrl = articles[position].media
                    val summary = articles[position].summary
                    val url = articles[position].link
                    val intent = Intent(itemView.context, ArticleDetailsActivity::class.java).apply {
                        putExtra("url", url)
                        putExtra("media", imageUrl)
                        putExtra("title", title)
                        putExtra("summary", summary)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val currentArticle = articles[position]
        holder.titleTextView.text = currentArticle.title
        holder.messageTextView.text = currentArticle.summary
        Glide.with(holder.itemView.context).load(currentArticle.media).into(holder.notificationImage)
    }

    override fun getItemCount() = articles.size

    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    companion object {
        fun getSavedArticlesFromSharedPreferences(context: Context): List<Article> {
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val gson = Gson()
            val articlesString = sharedPreferences.getString("articles", null)
            val articlesType = object : TypeToken<List<Article>>() {}.type
            return gson.fromJson(articlesString, articlesType) ?: emptyList()
        }
    }
}







