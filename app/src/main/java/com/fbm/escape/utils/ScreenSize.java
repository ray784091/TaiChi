package com.fbm.escape.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenSize {

    public static float width;

    public static float height;

    public static void obtainScreenSize(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }
}
