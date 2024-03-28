package com.newsportal.info_6134capstoneproject.response

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.newsportal.info_6134capstoneproject.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object NotificationHelper {

    private const val CHANNEL_ID = "News_Channel_ID"
    private const val NOTIFICATION_ID = 101

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        imageUrl: String,
        notificationPendingIntent: PendingIntent
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "News Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        GlobalScope.launch(Dispatchers.IO) {
            val bitmap = downloadImage(imageUrl)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)

            bitmap?.let {
                builder.setLargeIcon(it)
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun downloadImage(imageUrl: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }
}


