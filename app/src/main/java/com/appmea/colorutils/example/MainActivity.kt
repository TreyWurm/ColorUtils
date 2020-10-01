package com.appmea.colorutils.example

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.appmea.colorutils.MaterialColorUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorUtils = MaterialColorUtils(this)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_text).apply {
            setOnClickListener { }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                background = colorUtils.createRippleSurface(this)
            }
        }
    }
}