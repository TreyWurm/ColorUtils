package com.appmea.colorutils.library;

import android.content.Context;
import android.graphics.Color;
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
        selector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(rgbaToRGB(getColorSurface(), getColorRippleSurface())));
        selector.addState(new int[]{}, new ColorDrawable(getColorSurface()));
        return selector;
    }


    protected @ColorInt
    int rgbaToRGB(@ColorInt int background, @ColorInt int overlay) {
        float alpha = (0xFF & (overlay >> 24)) / 255f;
        float red = (0xFF & (overlay >> 16)) / 255f;
        float green = (0xFF & (overlay >> 8)) / 255f;
        float blue = (0xFF & (overlay)) / 255f;

        float redBackground = 0xFF & (background >> 16);
        float greenBackground = 0xFF & (background >> 8);
        float blueBackground = 0xFF & (background);

        float finalRed = (1 - alpha) * redBackground + alpha * red;
        float finalGreen = (1 - alpha) * greenBackground + alpha * green;
        float finalBlue = (1 - alpha) * blueBackground + alpha * blue;

        return Color.rgb((int) finalRed, (int) finalGreen, (int) finalBlue);
    }
}
