package com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.util.*


/**
 * MIT License
 *
 * Copyright (c) 2018 Alexander Shkirkov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

//Local binding service
class CustomLocalBindingService : Service() {

    val baseUrl : String = "https://api.openweathermap.org/data/2.5/weather"
    val appId : String = "a234bbe9dd715c7fb108587e489bcc35"

    private val binder = CustomBinder()

    companion object {
        private val TAG = CustomLocalBindingService::class.java.simpleName
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    var timer: Timer? = null
    var tTask: TimerTask? = null
    var interval: Long = 5000

    override fun onCreate() {
        super.onCreate()
        timer = Timer()
        schedule()
    }

    override fun onDestroy() {
        stop()
        super.onDestroy()
    }

    private fun createJsonObject(jsonString : String?) : String {

        var response = ""
        if (jsonString != null) {
            try {
                val jsonObject = JSONObject(jsonString)
                response += jsonObject.getString("name") + ": "
                response += (jsonObject.getJSONObject("main").getDouble("temp") - 273.15).toString()
            } catch (e : JSONException) {
                Log.e(TAG, "Json parsing error: " + e.localizedMessage)
            }
        } else {
            response = "Невозможно загрузить прогноз для этого города"
        }
        return "Local binding - $response"
    }


    private fun schedule() {
        if (tTask != null)
            tTask!!.cancel()
        if (interval > 0) {
            //val url = baseUrl + "?id=498817&appid=" + appId //Saint-Petersburg weather

            tTask = object : TimerTask() {
                override fun run() {
                    //val jsonStr = HttpHandler().makeServiceCall(url)
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(applicationContext, "Local binding: Hey!" /*createJsonObject(jsonStr)*/, Toast.LENGTH_LONG).show()
                    }
                }
            }
            timer!!.schedule(tTask, 0, interval)
       }
    }

    private fun stop() {
        if (tTask != null)
            tTask!!.cancel()
    }

    internal inner class CustomBinder : Binder() {
        val service: CustomLocalBindingService = this@CustomLocalBindingService
    }
}