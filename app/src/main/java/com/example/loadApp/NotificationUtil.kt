package com.example.loadApp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat


const val REQUEST_CODE = 0
const val NOTIFICATION_ID = 0
const val CHANNEL_NAME = "CHANNEL_1"
const val CHANNEL_ID = "channelId"


fun NotificationManager.sendCompleteDownloadNotification(
    context: Context,
    idText: Int,
    idStatus: Int
) {
    val intent = Intent(context, DetailActivity::class.java)
    intent.putExtra("Text", context.getString(idText))
    intent.putExtra("Status", context.getString(idStatus))
    val pendingIntent = PendingIntent.getActivity(
        context, REQUEST_CODE, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
    )

    val action = NotificationCompat.Action(
        R.drawable.ic_launcher_background,
        context.getString(R.string.notification_button),
        pendingIntent
    )

    val img = BitmapFactory.decodeResource(context.resources, R.drawable.downloadimg)

    val bigPic = NotificationCompat.BigPictureStyle()
        .bigPicture(img)
        .bigLargeIcon(null)

    val notificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_assistant_black_24dp)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(action)
            .setStyle(bigPic)
            .setLargeIcon(img)




    notify(NOTIFICATION_ID, notificationBuilder.build())

}

fun createChannel(context: Context, id: String, name: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)

        val notificationManager =
            context.getSystemService(NotificationManager::class.java) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }
}