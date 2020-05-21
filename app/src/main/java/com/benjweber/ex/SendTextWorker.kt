package com.benjweber.ex

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.benjweber.ex.ExNotificationManager.Companion.MESSAGES_CHANNEL_ID
import kotlin.random.Random

class SendTextWorker(private val context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val randText = (context as ExApp).textManager.getRandomText()
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("text", randText)
        }
        val pendingIntent = PendingIntent.getActivity(context, Random.nextInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var textNotification = NotificationCompat.Builder(context, MESSAGES_CHANNEL_ID)
            .setSmallIcon(R.drawable.sentiment_dissatisfied)
            .setContentTitle("Grace")
            .setContentText(randText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        context.exNotificationManager.postNotification(textNotification)
        return Result.success()
    }
}