package com.betanet.meta_ip18.feature

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        button_send.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("input_text", editText_input.text.toString())
            startActivityForResult(intent, REQUEST_CODE)
        }
        button_recycler_view.setOnClickListener {
            val intentRV = Intent(this@MainActivity, RecyclerViewActivity::class.java)
            startActivity(intentRV)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                textView_test.text = data?.getStringExtra("response_text")
            }
        }
    }
}
