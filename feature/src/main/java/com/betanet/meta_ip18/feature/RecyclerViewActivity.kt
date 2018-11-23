package com.betanet.meta_ip18.feature

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.recycler.RecyclerAdapter
import com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.recycler.RecyclerItem
import com.betanet.meta_ip18.feature.com.betanet.meta_ip18.feature.service.CustomDefaultService
import java.util.*
import kotlin.collections.ArrayList


class RecyclerViewActivity : AppCompatActivity() {

    val drawablesList = listOf(R.drawable.icons8_android, R.drawable.icons8_android_2, R.drawable.icons8_android_os, R.drawable.icons8_android_tablet,
            R.drawable.icons8_apk, R.drawable.icons8_apk_filled, R.drawable.icons8_apple_ipad, R.drawable.icons8_apple_ipad_2,
            R.drawable.icons8_cellular_phone, R.drawable.icons8_cellular_phone_2, R.drawable.icons8_cellular_phone_3, R.drawable.icons8_charged_battery,
            R.drawable.icons8_developer_mode, R.drawable.icons8_dolphin_emulator, R.drawable.icons8_facebook_messenger, R.drawable.icons8_feature_phone,
            R.drawable.icons8_feature_phone_2, R.drawable.icons8_google_news, R.drawable.icons8_google_play, R.drawable.icons8_line_filled,
            R.drawable.icons8_nfc, R.drawable.icons8_nfc_c, R.drawable.icons8_operating_system,  R.drawable.icons8_phone_with_a_touch_screen,
            R.drawable.icons8_playstore, R.drawable.icons8_playstore_2, R.drawable.icons8_rockstar_games_filled, R.drawable.icons8_stack_overflow,
            R.drawable.icons8_stumbleupon, R.drawable.icons8_tablet_slide, R.drawable.icons8_uc_browser_filled, R.drawable.icons8_whatsapp,
            R.drawable.icons8_whatsapp_2)

    val textList = listOf("Первый", "Второй", "Третий", "Четвертый", "Десятый", "Случайный", "Текст", "Слова", "Строка", "Надпись", "Ничего",
            "Пустой", "Двенадцатый", "Сообщение")

    val random = Random()

    val cache = Hashtable<Int, Drawable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val rv = findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val itemsList = ArrayList<RecyclerItem>()
        val itemsCount = 1000 + random.nextInt(500)
        val emptyDrawable = ResourcesCompat.getDrawable(resources, R.drawable.icons8_file_empty, null)!!
        for(i in 0..itemsCount) {
            cache[i] = ResourcesCompat.getDrawable(resources, drawablesList[random.nextInt(drawablesList.size)], null)
            itemsList.add(RecyclerItem(textList[random.nextInt(textList.size)],
                    emptyDrawable))
        }

        val adapter = RecyclerAdapter(itemsList, this, emptyDrawable)
        rv.adapter = adapter

        startService(Intent(this, CustomDefaultService::class.java).putExtra("time", 7))
    }
}
