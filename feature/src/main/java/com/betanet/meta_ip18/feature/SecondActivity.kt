package com.betanet.meta_ip18.feature

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        textView_receiver.text = intent.getStringExtra("input_text")
    }

    override fun onStart() {
        super.onStart()
        button_return.setOnClickListener {
            val intent = Intent()
            intent.putExtra("response_text", editText_return.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
