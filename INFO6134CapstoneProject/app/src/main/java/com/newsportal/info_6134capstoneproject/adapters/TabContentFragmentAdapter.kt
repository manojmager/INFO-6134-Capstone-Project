package com.newsportal.info_6134capstoneproject.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.ui.activities.WebViewActivity

class TabContentFragmentAdapter(
    private var articles: List<Article>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<TabContentFragmentAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_fragment_item, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun update(data: List<Article>) {
        articles = data
        notifyDataSetChanged()
    }

    inner class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewcardAuthor: TextView = view.findViewById(R.id.cardAuthor)
        private val textViewcardHeadline: TextView = view.findViewById(R.id.cardHeadline)
        private val imageView: ImageView = view.findViewById(R.id.cardImage)
        private val bookmark: ImageView = view.findViewById(R.id.cardBookmark)
        private val shareContent: ImageView = view.findViewById(R.id.cardShare)

        fun bind(article: Article) {
            textViewcardAuthor.text = article.author
            textViewcardHeadline.text = article.excerpt
            Glide.with(imageView.context).load(article.media).into(imageView)
        }

        init {
            textViewcardAuthor.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, WebViewActivity::class.java).apply {
                        putExtra("url", articles[position].link)
                    }
                    itemView.context.startActivity(intent)
                }
            }
            textViewcardHeadline.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, WebViewActivity::class.java).apply {
                        putExtra("url", articles[position].link)
                    }
                    itemView.context.startActivity(intent)
                }
            }
            imageView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, WebViewActivity::class.java).apply {
                        putExtra("url", articles[position].link)
                    }
                    itemView.context.startActivity(intent)
                }
            }
            shareContent.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = articles[position]
                    val shareMessage = "${article.excerpt}\nAuthor: ${article.author}\n${article.link}"
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareMessage)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    itemView.context.startActivity(shareIntent)
                }
            }
        }
    }
}
