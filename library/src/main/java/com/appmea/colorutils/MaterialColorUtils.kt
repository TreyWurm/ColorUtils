package com.appmea.colorutils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors

class MaterialColorUtils(val context: Context) {

    @ColorInt
    fun getColorPrimary(): Int {
        return MaterialColors.getColor(
            context, R.attr.colorPrimary, ContextCompat.getColor(context, R.color.default_color_primary)
        )
    }

    @ColorInt
    fun getColorOnPrimary(): Int {
        return MaterialColors.getColor(
            context, R.attr.colorOnPrimary, ContextCompat.getColor(context, R.color.default_color_on_primary)
        )
    }

    @ColorInt
    fun getColorSurface(): Int {
        return MaterialColors.getColor(
            context, R.attr.colorSurface, ContextCompat.getColor(context, R.color.default_color_surface)
        )
    }

    @ColorInt
    fun getColorRippleSurface(): Int {
        return MaterialColors.getColor(
            context, R.attr.colorRippleSurface, ContextCompat.getColor(context, R.color.default_color_ripple_surface)
        )
    }

    fun createRippleSurface(): StateListDrawable? {
        val selector = StateListDrawable()
        selector.addState(
            intArrayOf(android.R.attr.state_pressed),
            ColorDrawable(rgbaToRGB(getColorSurface(), getColorRippleSurface()))
        )
        selector.addState(intArrayOf(), ColorDrawable(getColorSurface()))
        return selector
    }


    @ColorInt
    fun rgbaToRGB(@ColorInt background: Int, @ColorInt overlay: Int): Int {
        val alpha = (0xFF and (overlay shr 24)) / 255f
        val red = (0xFF and (overlay shr 16)) / 255f
        val green = (0xFF and (overlay shr 8)) / 255f
        val blue = (0xFF and overlay) / 255f
        val redBackground = (0xFF and (background shr 16).toFloat().toInt()).toFloat()
        val greenBackground = (0xFF and (background shr 8).toFloat().toInt()).toFloat()
        val blueBackground = (0xFF and background.toFloat().toInt()).toFloat()
        val finalRed = (1 - alpha) * redBackground + alpha * red
        val finalGreen = (1 - alpha) * greenBackground + alpha * green
        val finalBlue = (1 - alpha) * blueBackground + alpha * blue
        return Color.rgb(finalRed.toInt(), finalGreen.toInt(), finalBlue.toInt())
    }
}