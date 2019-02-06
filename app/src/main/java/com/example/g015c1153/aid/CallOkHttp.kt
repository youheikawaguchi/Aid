package com.example.g015c1153.aid

import android.content.res.Resources
import android.util.Log
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class CallOkHttp {
    fun getRun(url: String): String{

        var responseText = ""   //何もなければ空文字が返る
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                responseText = response.body()!!.string()
                Log.i("getJson", responseText)  //受け取った値をログに出力
            }
        })
        return responseText
    }

    fun postRun(url: String, json: String): String{

        var responseText = ""   //何もなければ空文字が返る
        val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
                .addHeader("Content-Type", "text/plain; charset=utf-8")
                .url(url)
                .post(postBody)
                .build()

        launch {
            delay(1000)
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    responseText = response.body()!!.string()
                    Log.i("postJson", responseText)  //受け取った値をログに出力
                }
            })
        }
        Thread.sleep(1000)
        return responseText
    }
}