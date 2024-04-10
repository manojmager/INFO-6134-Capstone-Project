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
import com.newsportal.info_6134capstoneproject.R
import com.newsportal.info_6134capstoneproject.db.BookmarkViewModel
import com.newsportal.info_6134capstoneproject.model.Article
import com.newsportal.info_6134capstoneproject.model.DBArticle
import com.newsportal.info_6134capstoneproject.ui.activities.ArticleDetailsActivity
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TabContentFragmentAdapter (
    private var articles: List<Article>,
    private val bookmarkViewModel: BookmarkViewModel
) :
    RecyclerView.Adapter<TabContentFragmentAdapter.MViewHolder>() {
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
        private val imageView: ImageView = view.findViewById(R.id.cardImage)
        private val textViewCardAuthor: TextView = view.findViewById(R.id.cardAuthor)
        private val textViewCardHeadline: TextView = view.findViewById(R.id.cardHeadline)
        private val textViewCardPass: TextView = view.findViewById(R.id.cardPass)

        private val bookmarkBtn: ImageView = view.findViewById(R.id.cardBookmark)
        private val shareContentBtn: ImageView = view.findViewById(R.id.cardShare)


        fun bind(article: Article) {
            Glide.with(imageView.context)
                .load(article.media)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_error)
                .into(imageView)
            textViewCardAuthor.text = article.author
            textViewCardHeadline.text = article.excerpt

            val publishedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(article.published_date)
            val currentDate = Date()

            val diff = abs(currentDate.time - publishedDate.time)
            val days = diff / (1000 * 60 * 60 * 24)
            val hours = (diff / (1000 * 60 * 60)) % 24

            val displayString = if (days > 0) {
                "${days}d ago"
            } else {
                "${hours}h ago"
            }
            textViewCardPass.text = displayString
        }

        init {
            val isDarkMode = itemView.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getBoolean("is_dark_mode", false)

            if(isDarkMode){
                shareContentBtn.setImageResource(R.drawable.share_icon_dark)
                bookmarkBtn.setImageResource(R.drawable.inactive_bookmark_icon_dark)
            }else{
                shareContentBtn.setImageResource(R.drawable.share_icon)
                bookmarkBtn.setImageResource(R.drawable.inactive_bookmark_icon)
            }

            val clickListener = View.OnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, ArticleDetailsActivity::class.java).apply {
                        putExtra("url", articles[position].link)
                        putExtra("media", articles[position].media)
                        putExtra("title", articles[position].title)
                        putExtra("summary", articles[position].summary)
                    }
                    itemView.context.startActivity(intent)
                }
            }

            textViewCardAuthor.setOnClickListener(clickListener)
            textViewCardHeadline.setOnClickListener(clickListener)
            imageView.setOnClickListener(clickListener)

            bookmarkBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val title = articles[position].title
                    val author = articles[position].author
                    val published_date = articles[position].published_date
                    val link = articles[position].link
                    val media = articles[position].media
                    val excerpt = articles[position].excerpt
                    val summary = articles[position].summary
                    val topic = articles[position].topic

                    val dbArticle = DBArticle(_id = 0, title, author, published_date,link, media,excerpt, summary, topic)
                    bookmarkViewModel.addBookmark(dbArticle)
                    if (isDarkMode){
                        bookmarkBtn.setImageResource(R.drawable.active_bookmark_icon_dark)
                    }else{
                        bookmarkBtn.setImageResource(R.drawable.active_bookmark_icon)
                    }

                }
            }

            shareContentBtn.setOnClickListener {
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
