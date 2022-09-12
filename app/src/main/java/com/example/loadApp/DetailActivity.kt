package com.example.loadApp

import android.app.NotificationManager
import android.content.Intent
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


        val txt_fileName = findViewById<TextView>(R.id.txt_fileName)
        txt_fileName.text = "Gilde"
        val txt_status = findViewById<TextView>(R.id.txt_status)
        txt_status.text = "Sucess"

        cancelNotification()

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