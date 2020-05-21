package com.benjweber.ex

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ExNotificationManager(private val context: Context) {
    private val workManager = WorkManager.getInstance(context)
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    init {
        createMessagesChannel()
        startExtraCreditWorker()
    }

    fun releaseHer() {
        if (beastIsLoose()) blockHer()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val req = PeriodicWorkRequestBuilder<SendTextWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .addTag(TEXT_REQUEST_TAG)
            .build()
        workManager.enqueue(req)
    }

    fun blockHer() {
        workManager.cancelAllWorkByTag(TEXT_REQUEST_TAG)
        Toast.makeText(context, "She won't be bothering you anymore.", Toast.LENGTH_SHORT).show()
    }

    private fun beastIsLoose(): Boolean {
        return when (workManager.getWorkInfosByTag(TEXT_REQUEST_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING, WorkInfo.State.RUNNING -> true
            else -> false
        }
    }

    fun startExtraCreditWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val req = PeriodicWorkRequestBuilder<ExtraCreditWorker>(2, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(req)
    }

    fun postNotification(notification: Notification) {
        notificationManagerCompat.notify(Random.nextInt(), notification)
    }

    private fun createMessagesChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Messages"
            val descriptionText = "Messages that will make you feel unsafe."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MESSAGES_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    companion object {
        const val TEXT_REQUEST_TAG = "TEXT_REQUEST_TAG"
        const val MESSAGES_CHANNEL_ID = "DANGEROUS_CHANNEL_ID"
    }
}

// -----------------------------------------

class ExtraCreditWorker(private val context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        (context as ExApp).textManager.fetchTexts()
        return Result.Success()
    }
}