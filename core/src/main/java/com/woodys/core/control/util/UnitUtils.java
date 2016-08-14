package com.woodys.core.control.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 单位工具类
 *
 * @author chznzhen
 */
public class UnitUtils {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        int value = -1;
        if (null != context) {
            value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
        }
        return value;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int sp2px(Context context, float pxValue) {
        int value = -1;
        if (null != context) {
            value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, context.getResources().getDisplayMetrics());
        }
        return value;
    }

    public static float px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

}
