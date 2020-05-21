package com.benjweber.ex

import android.app.Application

class ExApp: Application() {
//    private lateinit var texts: List<String>
    lateinit var textManager: TextManager private set
    lateinit var exNotificationManager: ExNotificationManager private set

    override fun onCreate() {
        super.onCreate()
        textManager = TextManager(this).apply {
            fetchTexts()
        }
        exNotificationManager = ExNotificationManager(this)
    }
}