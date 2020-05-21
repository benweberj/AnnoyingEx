package com.benjweber.ex

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class SendTextWorker(private val context: Context, params: WorkerParameters): Worker(context, params) {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    init { createChannel() }

    override fun doWork(): Result {
        var textNotification = NotificationCompat.Builder(context, DANGEROUS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Grace")
            .setContentText((context as ExApp).textManager.getRandomText())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManagerCompat.notify(69, textNotification)
        return Result.success()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Dangerous Notifications"
            val descriptionText = "Messages that will make you feel unsafe."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DANGEROUS_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    companion object { const val DANGEROUS_CHANNEL_ID = "DANGEROUS_CHANNEL_ID" }
}