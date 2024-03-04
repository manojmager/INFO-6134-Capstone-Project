package com.newsportal.info_6134capstoneproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.newsportal.info_6134capstoneproject.R

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView: WebView = findViewById(R.id.webView)
        val url = intent.getStringExtra("url")

        webView.webViewClient = WebViewClient()
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        // Check if the WebView can navigate back
        val webView = findViewById<WebView>(R.id.webView)
        if (webView.canGoBack()) {
            webView.goBack() // If possible, go back in the WebView history
        } else {
            super.onBackPressed() // Otherwise, proceed with normal back press behavior
        }
    }
}