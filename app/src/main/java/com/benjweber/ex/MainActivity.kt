package com.benjweber.ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exNotificationManager = (application as ExApp).exNotificationManager

        btnStart.setOnClickListener {
            exNotificationManager.releaseHer()
        }
        btnStop.setOnClickListener {
            exNotificationManager.blockHer()
        }
        if (intent.hasExtra("text")) {
            tvMessage.text = Html.fromHtml("<strong>Grace</strong>: <em>${intent.getStringExtra("text")}</em>")
        }
    }
}