package com.example.loadApp

import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbarDetail))

        setData()

        cancelNotification()

    }

    private fun setData() {
        val fileName = findViewById<TextView>(R.id.txt_fileName)
        fileName.text = intent.getStringExtra("Text")

        val status = findViewById<TextView>(R.id.txt_status)
        val text = intent.getStringExtra("Status")
        status.text = text
        status.setTextColor(if (text == getString(R.string.Success)) getColor(R.color.green) else Color.RED)
    }

    private fun cancelNotification() {
        val notificationManager = ContextCompat.getSystemService(
            this, NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }

    fun okClicked(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}