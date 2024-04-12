package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.newsportal.info_6134capstoneproject.R

class ArticleDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val toolbar: Toolbar = findViewById(R.id.tbArticleDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val imageView: ImageView = findViewById(R.id.ad_image_view)
        val btnViewSite: Button = findViewById(R.id.ad_btn_view)
        val textViewTitle: TextView = findViewById(R.id.ad_title)
        val textViewSummery: TextView = findViewById(R.id.ad_summary)

        val url = intent.getStringExtra("url")
        val media = intent.getStringExtra("media")
        val title = intent.getStringExtra("title")
        val summery = intent.getStringExtra("summary")
        Log.d("TAG", "onCreate: $summery")

        textViewTitle.textSize = 24f
        Glide.with(imageView.context)
            .load(media)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_error)
            .into(imageView)
        textViewTitle.text = title

        val formattedSummary = summery?.replace(". ", ".\n\n")
        textViewSummery.textSize = 18f
        textViewSummery.setLineSpacing(10f, 1f)
        textViewSummery.text = formattedSummary

        btnViewSite.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra("url", url)
            }
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}