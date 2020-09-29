package com.appmea.colorutils.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class MaterialColorUtils {

    private final Context context;

    public MaterialColorUtils(Context context) {
        this.context = context;
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true)) {
            return typedValue.data;
        }
        return ContextCompat.getColor(context, R.color.default_color_primary);
    }


    public int getColorOnPrimary() {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.colorOnPrimary, typedValue, true)) {
            return typedValue.data;
        }
        return ContextCompat.getColor(context, R.color.default_text_color);
    }

    public int getColorSurface() {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.colorSurface, typedValue, true)) {
            return typedValue.data;
        }
        return ContextCompat.getColor(context, R.color.default_ripple_color);
    }

    @ColorInt
    public int getColorRippleSurface() {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.colorRippleSurface, typedValue, true)) {
            return typedValue.data;
        }
        return ContextCompat.getColor(context, R.color.default_ripple_color);
    }

    public StateListDrawable createRippleSurface() {
        StateListDrawable selector = new StateListDrawable();

        selector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(getColorRippleSurface()));
        selector.addState(new int[]{}, new ColorDrawable(getColorSurface()));
        return selector;
    }

    public Color valueOf(@ColorInt int color) {
        float r = ((color >> 16) & 0xff) / 255.0f;
        float g = ((color >> 8) & 0xff) / 255.0f;
        float b = ((color) & 0xff) / 255.0f;
        float a = ((color >> 24) & 0xff) / 255.0f;
        Color newColor = new Color();
        Color opaqueRed = Color.valueOf(0xffff0000); // from a color int
        Color translucentRed = Color.valueOf(1.0f, 0.0f, 0.0f, 0.5f);
        return Color.argb(a, r, g, b);
    }
}
