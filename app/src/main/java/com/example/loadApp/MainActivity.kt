package com.example.loadApp

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var url: URLS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbarMain))

        createChannel(this, CHANNEL_ID, CHANNEL_NAME)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        findViewById<LoadingButton>(R.id.custom_button).setOnClickListener { download() }
    }

    //when Download Finish
    private fun sendNotification() {
        val notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendCompleteDownloadNotification(this)
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (id == downloadID) sendNotification()
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(url.value))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)
    }

}

private enum class URLS(val value: String) {
    Glide("https://github.com/bumptech/glide"),
    LoadApp("https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"),
    Retrofit("https://github.com/square/retrofit")
}