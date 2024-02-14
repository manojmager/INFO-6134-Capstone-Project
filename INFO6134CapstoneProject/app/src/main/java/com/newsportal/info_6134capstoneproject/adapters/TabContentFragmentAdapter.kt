package com.newsportal.info_6134capstoneproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Article
class TabContentFragmentAdapter (private var articles: List<Article>) :
    RecyclerView.Adapter<TabContentFragmentAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_fragment_item, parent, false)
        return MViewHolder(view)
    }
    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun update(data: List<Article>) {
        articles = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewcardAuthor: TextView = view.findViewById(R.id.cardAuthor)
        private val textViewcardHeadline: TextView = view.findViewById(R.id.cardHeadline)
        private val imageView: ImageView = view.findViewById(R.id.cardImage)
        fun bind(museum: Article) {
            textViewcardAuthor.text = museum.author?.capitalize()
            textViewcardHeadline.text = museum.excerpt
            Glide.with(imageView.context).load(museum.media).into(imageView)
        }
    }
}