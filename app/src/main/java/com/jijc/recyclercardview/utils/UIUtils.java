package com.jijc.recyclercardview.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Description:
 * Created by jijc on 2016/8/29.
 * PackageName: com.jijc.recyclercardview.utils
 */
public class UIUtils {
    /**
     * 像素转换
     */
    public static int dip2pixel(Context context, float n) {
        int value = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, n, context.getResources()
                        .getDisplayMetrics());
        return value;
    }

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
