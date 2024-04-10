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
import com.newsportal.info_6134capstoneproject.model.DBArticle
import com.newsportal.info_6134capstoneproject.ui.activities.ArticleDetailsActivity

class BookmarkAdapter(private val bookmarkViewModel: BookmarkViewModel) : RecyclerView.Adapter<BookmarkAdapter.MViewHolder>() {

    private var bookmarkList = emptyList<DBArticle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_fragment_item, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    fun update(data: List<DBArticle>) {
        this.bookmarkList = data
        notifyDataSetChanged()
    }

    inner class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewCardAuthor: TextView = view.findViewById(R.id.cardAuthor)
        private val textViewCardHeadline: TextView = view.findViewById(R.id.cardHeadline)
        private val imageView: ImageView = view.findViewById(R.id.cardImage)

        private val bookmarkBtn: ImageView = view.findViewById(R.id.cardBookmark)
        private val shareContentBtn: ImageView = view.findViewById(R.id.cardShare)


        fun bind(dbArticle: DBArticle) {
            textViewCardAuthor.text = dbArticle.author
            textViewCardHeadline.text = dbArticle.excerpt
            Glide.with(imageView.context).load(dbArticle.media).into(imageView)
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
                        putExtra("url", bookmarkList[position].link)
                        putExtra("media", bookmarkList[position].media)
                        putExtra("title", bookmarkList[position].title)
                        putExtra("summary", bookmarkList[position].summary)
                    }
                    itemView.context.startActivity(intent)
                }
            }

            textViewCardAuthor.setOnClickListener(clickListener)
            textViewCardHeadline.setOnClickListener(clickListener)
            imageView.setOnClickListener(clickListener)

            if (isDarkMode){
                bookmarkBtn.setImageResource(R.drawable.active_bookmark_icon_dark)
            }else{
                bookmarkBtn.setImageResource(R.drawable.active_bookmark_icon)
            }

            bookmarkBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val articleToDelete = bookmarkList[position]
                    bookmarkViewModel.deleteBookmark(articleToDelete)
                }
            }

            shareContentBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = bookmarkList[position]
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