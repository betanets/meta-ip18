package com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


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

class CustomDefaultService : Service() {

    internal var es: ExecutorService? = null
    internal var someRes: Any? = null

    override fun onCreate() {
        super.onCreate()
        es = Executors.newFixedThreadPool(1)
        someRes = Any()
    }

    override fun onDestroy() {
        super.onDestroy()
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "DefaultService: Destroyed", Toast.LENGTH_LONG).show()
        }
        someRes = null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "DefaultService#$startId: Started command", Toast.LENGTH_LONG).show()
        }
        val time = intent.getIntExtra("time", 1)
        val mr = CustomDefaultServiceRun(time, startId)
        es!!.execute(mr)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    internal inner class CustomDefaultServiceRun(var time: Int, var startId: Int) : Runnable {

        init {
            //Log.d(LOG_TAG, "MyRun#$startId create")
        }

        override fun run() {
            try {
                TimeUnit.SECONDS.sleep(time.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            stop()
        }

        fun stop() {
            stopSelf(startId)
        }
    }
}