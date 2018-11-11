package com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject


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


class CustomIntentService : IntentService("customIntentService") {

    val baseUrl : String = "https://api.openweathermap.org/data/2.5/weather"
    val appId : String = "a234bbe9dd715c7fb108587e489bcc35"

    companion object {
        private val TAG = CustomIntentService::class.java.simpleName
    }

    fun createJsonObject(jsonString : String?) : String {

        var response = ""
        if (jsonString != null) {
            try {
                var jsonObject = JSONObject(jsonString)
                response += jsonObject.getString("name") + ": "
                response += jsonObject.getJSONObject("main").getJSONObject("temp")
            } catch (e : JSONException) {
                Log.e(TAG, "Json parsing error: " + e.localizedMessage)
            }
        } else {
            response = "Невозможно загрузить прогноз для этого города"
        }
        return response
    }

    override fun onHandleIntent(intent: Intent) {
        val cityId = intent.getIntExtra("cityId", 0)
        val cityName = intent.getStringExtra("cityName")

        var url = baseUrl + "?id=" + cityId + "&appid=" + appId

        val jsonStr = HttpHandler().makeServiceCall(url)
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, createJsonObject(jsonStr), Toast.LENGTH_LONG).show()
        }

    }
}