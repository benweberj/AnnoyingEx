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
    private var texts: List<String>? = null
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
        return if (texts === null || texts!!.isEmpty()) "unable to retrieve message" else texts!![Random.nextInt(texts!!.size-1)]
    }
}

data class Messages(val messages: List<String>)