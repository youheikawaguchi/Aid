package com.example.g015c1153.aid

import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import java.io.IOException

class CallOkHttp {
    fun getRun(url: String, json: String): String?{

        var responseText: String? = ""
        val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                responseText = response.body()?.string()
            }
        })
        return responseText
    }

    fun postRun(url: String, json: String): String?{

        var responseText: String? = ""
        val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .post(postBody)
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                responseText = response.body()?.string()
                Log.i("postJson",responseText)
            }
        })
        return responseText
    }
}