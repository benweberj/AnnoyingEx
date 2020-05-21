package com.benjweber.ex

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlin.random.Random

class TextManager(private val context: Context) {
    lateinit var texts: List<String>
    private val q: RequestQueue = Volley.newRequestQueue(context)
    private val textsEndpoint = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

    fun fetchTexts() {
        val req = StringRequest(
            Request.Method.GET, textsEndpoint,
            { res ->
                texts = Gson().fromJson(res, Messages::class.java).messages
            }, {err ->
                Toast.makeText(context, "Sorry, it appears your ex has blocked you.", Toast.LENGTH_LONG).show()
                Log.i("bjw", "[Error] Response: ${err.networkResponse.statusCode} - ${err.networkResponse.data}")
            })
        q.add(req)
    }

    fun getRandomText(): String {
        return texts[Random.nextInt(texts.size)]
    }
}

data class Messages(val messages: List<String>)

