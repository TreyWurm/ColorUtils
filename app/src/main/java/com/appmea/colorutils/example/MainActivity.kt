package com.appmea.colorutils.example

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.appmea.colorutils.library.MaterialColorUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorUtils = MaterialColorUtils(this)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_text).apply {
            setOnClickListener { }
            background = colorUtils.createRippleSurface()
        }
    }
}