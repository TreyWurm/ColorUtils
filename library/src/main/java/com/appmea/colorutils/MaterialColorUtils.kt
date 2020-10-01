package com.appmea.colorutils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
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

    @ColorInt
    fun getColorBackground(): Int {
        return MaterialColors.getColor(
            context, android.R.attr.colorBackground, ContextCompat.getColor(context, R.color.default_color_surface)
        )
    }

    /**
     * Use for SDK < 21, else use [com.appmea.colorutils.R.drawable.ripple_surface]
     *
     * Creates a Selector with default color [com.appmea.colorutils.R.attr.colorSurface] and pressed state color [com.appmea.colorutils.R.attr.colorRippleSurface]
     * If those attributes are not defined, default colors will be used
     *
     * In case [com.appmea.colorutils.R.attr.colorSurface] is not fully opaque we need to merge with the first views/parents background color that is fully opaque
     */
    fun createRippleSurface(view: View): StateListDrawable? {
        // We need an opaque background to overlay
        // We are gonna check backgrounds for first opaque background color or resort to WHITE as default
        var colorSurface = getColorSurface()
        val colorViewBackground = view.getRGBBackgroundColor()
        val colorBackground = getColorBackground()

        var opaqueSurface = if (isOpaque(colorSurface)) {
            colorSurface
        } else if (colorViewBackground != null && isOpaque(colorViewBackground)) {
            overlayToRgb(colorViewBackground, colorSurface)
        }  else if (colorViewBackground != null && isOpaque(colorBackground)) {
            overlayToRgb(colorBackground, colorSurface)
        } else {
            overlayToRgb(Color.WHITE, colorSurface)
        }


        val selector = StateListDrawable()
        selector.addState(
            intArrayOf(android.R.attr.state_pressed),
            ColorDrawable(overlayToRgb(opaqueSurface, limitAlpha(getColorRippleSurface())))
        )
        selector.addState(intArrayOf(), ColorDrawable(colorSurface))
        return selector
    }


    /**
     * Returns an opaque color corresponding to overlaying the background with the overlay
     *
     * @param background Needs to be an opaque RGB color; otherwise it will be combined with solid white
     * @param overlay (Transparent) overlay to
     */
    @ColorInt
    fun overlayToRgb(@ColorInt background: Int, @ColorInt overlay: Int): Int {
        var rgbBackground = background
        // Need a RGB background without alpha to work, so we get a RGB by combining transparent background with a solid white
        if ((0xFF and (rgbBackground shr 24)) < 255) {
            rgbBackground = overlayToRgb(Color.WHITE, background)
        }
        val alpha = (0xFF and (overlay shr 24)) / 255f

        val red = (0xFF and (overlay shr 16)) / 255f
        val green = (0xFF and (overlay shr 8)) / 255f
        val blue = (0xFF and overlay) / 255f

        val redBackground = (0xFF and (rgbBackground shr 16)) / 255f
        val greenBackground = (0xFF and (rgbBackground shr 8)) / 255f
        val blueBackground = (0xFF and rgbBackground) / 255f

        val finalRed = (1 - alpha) * redBackground + alpha * red
        val finalGreen = (1 - alpha) * greenBackground + alpha * green
        val finalBlue = (1 - alpha) * blueBackground + alpha * blue

        return Color.rgb((finalRed * 255f).toInt(), (finalGreen * 255f).toInt(), (finalBlue * 255f).toInt())
    }


    /**
     * Returns either the background color of the view itself, if it is opaque, or the parents background color otherwise if defined
     */
    fun View.getRGBBackgroundColor(): Int? {
        return when {
            isOpaque((background as? ColorDrawable)?.color) -> (background as ColorDrawable).color
            isOpaque(((parent as? View)?.background as? ColorDrawable)?.color) -> ((parent as View).background as ColorDrawable).color
            else -> null
        }
    }

    /**
     * Limits the given colors alpha value to at most half
     */
    fun limitAlpha(@ColorInt int: Int): Int {
        return if (Color.alpha(int) > 128) {
            (int and 0x00FFFFFF) or -0x80000000
        } else {
            int
        }
    }

    /**
     * Checks if a color is fully opaque
     */
    fun isOpaque(@ColorInt int: Int?): Boolean {
        return if (int == null) {
            false
        } else {
            (0xFF and (int shr 24)) >= 255
        }
    }
}