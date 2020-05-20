package com.benjweber.ex

import android.app.Application

class ExApp: Application() {
//    private lateinit var texts: List<String>
    val textManager: TextManager = TextManager(this)

    init {

    }

}