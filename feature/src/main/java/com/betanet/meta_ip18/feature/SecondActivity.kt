package com.betanet.meta_ip18.feature

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service.CustomLocalBindingService
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    var bound = false
    var sConn: ServiceConnection? = null
    var serviceIntent: Intent? = null
    var myService: CustomLocalBindingService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView_receiver.text = intent.getStringExtra("input_text")

        serviceIntent = Intent(this, CustomLocalBindingService::class.java)
        sConn = object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                myService = (binder as CustomLocalBindingService.CustomBinder).service
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                bound = false
            }
        }

    }

    override fun onStart() {
        super.onStart()
        button_return.setOnClickListener {
            val intent = Intent()
            intent.putExtra("response_text", editText_return.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
        bindService(serviceIntent, sConn, 0)
        startService(serviceIntent)
    }


    override fun onPause() {
        super.onPause()
        if (!bound)
            return
        stopService(serviceIntent)
        unbindService(sConn)
        bound = false
    }
}
