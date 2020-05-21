package com.benjweber.ex

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class ExNotificationManager(private val context: Context) {
    private var workManager = WorkManager.getInstance(context)

    fun releaseHer() {
        if (beastIsLoose()) blockHer()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val req = OneTimeWorkRequestBuilder<SendTextWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        //        val req = PeriodicWorkRequestBuilder<SendTextWorker>(20, TimeUnit.MINUTES)
        //            .setInitialDelay(5, TimeUnit.SECONDS)
        //            .setConstraints(constraints)
        //            .build()
        //            .addTag(TEXT_REQUEST_TAG)
        workManager.enqueue(req)
    }

    fun blockHer() {
        workManager.cancelAllWorkByTag(TEXT_REQUEST_TAG)
    }

    private fun beastIsLoose(): Boolean {
        return when (workManager.getWorkInfosByTag(TEXT_REQUEST_TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING, WorkInfo.State.RUNNING -> true
            else -> false
        }
    }

    companion object { const val TEXT_REQUEST_TAG = "TEXT_REQUEST_TAG" }
}