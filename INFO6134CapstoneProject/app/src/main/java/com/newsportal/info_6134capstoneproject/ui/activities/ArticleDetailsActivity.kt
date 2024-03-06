package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.newsportal.info_6134capstoneproject.R
class ArticleDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val imageView: ImageView = findViewById(R.id.ad_image_view)
        val btnViewSite: Button = findViewById(R.id.ad_btn_view)
        val textViewTitle: TextView = findViewById(R.id.ad_title)
        val textViewSummery: TextView = findViewById(R.id.ad_summery)

        val url = intent.getStringExtra("url")
        val media = intent.getStringExtra("media")
        val title = intent.getStringExtra("title")
        val summery = intent.getStringExtra("summary")

        Glide.with(imageView.context).load(media).into(imageView)
        textViewTitle.text = title
        textViewSummery.text = summery

        btnViewSite.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra("url", url)
            }
            startActivity(intent)
        }
    }
}