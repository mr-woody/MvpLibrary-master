package com.woodys.core.control.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.woodys.core.control.logcat.Logcat;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class InputMethodUtils {
    private static Runnable mShowImeRunnable;

    /**
     * 设置软键盘是否显示
     *
     * @param visible
     */
    public static void setImeVisibility(final View view, final boolean visible) {
        if (view == null) {
            return;
        }
        if (null == mShowImeRunnable) {
            mShowImeRunnable = () -> {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    showSoftInputUnchecked(view, imm, 0);
                }
            };
        }
        if (visible) {
            view.post(mShowImeRunnable);
        } else {
            view.removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                Observable.timer(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(delay -> {
                    view.clearFocus();
                    boolean b = imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Logcat.d("setImeVisibility %b",b);
                });
            }
        }
    }

    /**
     * 强制显示输入法
     *
     * @param view
     * @param imm
     * @param flags
     */
    private static void showSoftInputUnchecked(View view, InputMethodManager imm, int flags) {
        try {
            Method method = imm.getClass().getMethod("showSoftInputUnchecked", int.class, ResultReceiver.class);
            method.setAccessible(true);
            method.invoke(imm, flags, null);
        } catch (Exception e) {
            imm.showSoftInput(view, flags);
        }
    }

    /**
     * 隐藏输入法
     *
     * @param context
     */
    public static void hideSoftInput(@NonNull Context context, @NonNull View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     */
    public static void showSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏输入法
     */
    public static void hideSoftInput(Activity activity) {
        if (null != activity && activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 多少时间后显示软键盘
     */
    public static void showInputMethod(final View view, long delayMillis) {
        // 显示输入法
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodUtils.showInputMethod(view);
            }
        }, delayMillis);
    }
}
