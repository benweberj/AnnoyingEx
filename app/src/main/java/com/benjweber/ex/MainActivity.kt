package com.benjweber.ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Constraints
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exApp = application as ExApp
        val textManager = exApp.textManager
        val exNotificationManager = exApp.exNotificationManager

        btnStart.setOnClickListener {
            exNotificationManager.releaseHer()
        }
        btnStop.setOnClickListener {
            exNotificationManager.blockHer()
        }
    }
}