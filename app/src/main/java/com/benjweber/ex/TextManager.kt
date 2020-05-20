package com.benjweber.ex

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class TextManager(private val context: Context) {
    private lateinit var texts: List<String>
    private val q: RequestQueue = Volley.newRequestQueue(context)
    private val textsEndpoint = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/ex_messages.json"

    init {

    }

    fun fetchTexts(onTextsFetched: (List<String>) -> Unit) {
        val req = StringRequest(
            Request.Method.GET, textsEndpoint,
            { res ->
                allSongs = Gson().fromJson(res, AllSongs::class.java).songs
                onSongsFetched(allSongs as List<Song>)
            }, {err ->
                Toast.makeText(context, "Sorry, there was an error retrieving you library.", Toast.LENGTH_LONG).show()
                Log.i("bjw", "Error. Response: ${err.networkResponse.statusCode}")
            })
        q.add(req)
    }
}

