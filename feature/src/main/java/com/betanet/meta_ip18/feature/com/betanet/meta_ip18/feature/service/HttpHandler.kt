package com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service

import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL


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


class HttpHandler {

    companion object {
        private val TAG = HttpHandler::class.java.simpleName
    }

    fun makeServiceCall(reqUrl: String): String? {
        var response: String? = null
        try {
            val url = URL(reqUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 15000
            val inStream = BufferedInputStream(conn.inputStream)
            response = convertStreamToString(inStream)
        } catch (e: MalformedURLException) {
            Log.e(TAG, "MalformedURLException: " + e.localizedMessage)
        } catch (e: ProtocolException) {
            Log.e(TAG, "ProtocolException: " + e.localizedMessage)
        } catch (e: IOException) {
            Log.e(TAG, "IOException: " + e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }

        return response
    }

    private fun convertStreamToString(inStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inStream))
        val sb = StringBuilder()

        var line: String?
        try {
            line = reader.readLine()
            while (line != null) {
                sb.append(line).append('\n')
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return sb.toString()
    }
}