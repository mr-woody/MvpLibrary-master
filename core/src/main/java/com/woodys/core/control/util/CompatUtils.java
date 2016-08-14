package com.woodys.core.control.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by cz on 15/7/25.
 * 兼容代码执行工具类
 */
public class CompatUtils {
    /**
     * 执行一段代码
     */
    public static void runAction(OnCompatListener listener) {
        if (null == listener) return;
        boolean isCompat = Build.VERSION_CODES.HONEYCOMB >= Build.VERSION.SDK_INT;
        listener.action(isCompat, Build.VERSION.SDK_INT);
    }

    /**
     * 如果无须兼容,在整体布局变化时调用事件
     *
     * @param view
     * @param listener
     */
    public static void runAction(final View view, OnCompatListener listener) {
        if (null == listener) return;
        final boolean isCompat = Build.VERSION_CODES.HONEYCOMB >= Build.VERSION.SDK_INT;
        if (isCompat) {
            listener.action(isCompat, Build.VERSION.SDK_INT);
        } else {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int width = view.getWidth();
                    if (0 != width) {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        listener.action(isCompat, Build.VERSION.SDK_INT);
                    }
                }
            });
        }
    }

    /**
     * 执行view动画事件
     *
     * @param view 执行view对象
     * @param show view显示隐藏
     */
    public static void runViewVisibleAction(@NonNull View view, final boolean show) {
        boolean isCompat = Build.VERSION_CODES.HONEYCOMB >= Build.VERSION.SDK_INT;
        if (isCompat) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            ViewCompat.animate(view).alpha(show ? 1.0f : 0f).setDuration(300).setListener(new ViewPropertyAnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(View view) {
                    view.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public interface OnCompatListener {
        void action(boolean isCompat, int version);
    }
}
