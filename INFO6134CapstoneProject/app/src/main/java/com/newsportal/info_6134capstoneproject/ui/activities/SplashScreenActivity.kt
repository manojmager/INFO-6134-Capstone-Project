package com.newsportal.info_6134capstoneproject.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.newsportal.info_6134capstoneproject.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val videoView = findViewById<VideoView>(R.id.videoView)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.logo

        val videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.start()
        }

        videoView.setOnCompletionListener {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }
}