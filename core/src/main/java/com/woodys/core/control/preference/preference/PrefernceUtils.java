package com.woodys.core.control.preference.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.woodys.core.BaseApp;

/**
 * 配置项管理对象
 *
 * @author momo
 * @version 从prefernceName中取值, 根据角标取值, 并设置.
 * @Date 2014/11/28
 */
public class PrefernceUtils {
    // 配置项名称
    private static final String DEFAULT_PREFERENCE = "jy_config";
    private static final String DEFAULT_KEY = "config";
    private static final String DEFAULT_VALUE = "-1";
    private static final int DEFAULT_INT_VALUE = -1;
    private static Context appContext;


    /**
     * 重置配置项
     */
    public static void resetPreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_PREFERENCE, Context.MODE_MULTI_PROCESS);
        String value = preferences.getString(DEFAULT_KEY, null);
        if (TextUtils.isEmpty(value)) {
            resetPreference(context);
        } else {
            String[] array = value.split("\\|");
            if (null != array && 0 < array.length) {
                SharedPreferences.Editor editor = preferences.edit();
                for (int i = 0; i < array.length; i++) {
                    editor.putString(String.valueOf(i), array[i]).commit();
                }
                editor.putString(DEFAULT_KEY, null).commit();
            } else {
                resetPreference(context);
            }
        }
    }

    private static SharedPreferences getSharedPreferences() {
        if (null == appContext) {
            appContext = BaseApp.getContext();
        }
        return appContext.getSharedPreferences(DEFAULT_PREFERENCE, Context.MODE_MULTI_PROCESS);
    }

    private static SharedPreferences.Editor getPreferenceEditor() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.edit();
    }


    public static void remove(int key) {
        SharedPreferences.Editor editor = getPreferenceEditor();
        editor.remove(String.valueOf(key)).commit();
    }


    public static String getDefaultValue() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(DEFAULT_KEY, null);
    }


    /**
     * 根据角标获取 默认值 -1;
     *
     * @param index
     * @return
     */
    public static int getInt(int index) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        int value;
        try {
            value = Integer.valueOf(sharedPreferences.getString(String.valueOf(index), DEFAULT_VALUE));
        } catch (NumberFormatException e) {
            value = DEFAULT_INT_VALUE;
        }
        return value;
    }

    public static int getInt(int index, int defaultValue) {
        int value = getInt(index);
        return DEFAULT_INT_VALUE == value ? defaultValue : value;
    }


    /**
     * 根据角标获取 默认值 -1;
     *
     * @param index
     * @return
     */
    public static long getLong(int index) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        long value;
        try {
            value = Long.valueOf(sharedPreferences.getString(String.valueOf(index), DEFAULT_VALUE));
        } catch (NumberFormatException e) {
            value = DEFAULT_INT_VALUE;
        }
        return value;
    }


    /**
     * 获得boolean值
     *
     * @param index
     * @return
     */
    public static boolean getBoolean(int index) {
        return 1 == getInt(index);
    }

    public static boolean getBoolean(int index, boolean defaultVal) {
        return 1 == getInt(index, defaultVal ? 1 : 0);
    }


    /**
     * 取反boolean值
     *
     * @param index 默认值
     * @return
     */
    public static boolean getRvsBoolean(int index) {
        return !getBoolean(index);
    }


    /**
     * 根据角标获取 默认值 null;
     *
     * @param index
     * @return
     */
    public static String getString(int index) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String value = sharedPreferences.getString(String.valueOf(index), null);
        if (DEFAULT_VALUE.equals(value)) {
            value = null;
        }
        return value;
    }

    /*
    * @param index
    * @return
    */
    public static String getString(int index, String default_value) {
        SharedPreferences preference = getSharedPreferences();
        return preference.getString(String.valueOf(index), default_value);
    }

    public static void setInt(int index, int value) {
        SharedPreferences.Editor editor = getPreferenceEditor();
        editor.putString(String.valueOf(index), String.valueOf(value)).commit();
    }

    public static void setLong(int index, long value) {
        SharedPreferences.Editor editor = getPreferenceEditor();
        editor.putString(String.valueOf(index), String.valueOf(value)).commit();
    }

    public static void setString(int index, String value) {
        SharedPreferences.Editor editor = getPreferenceEditor();
        editor.putString(String.valueOf(index), value).commit();
    }


    public static void setBoolean(int index, Boolean value) {
        SharedPreferences.Editor editor = getPreferenceEditor();
        editor.putString(String.valueOf(index), String.valueOf(value ? 1 : 0)).commit();
    }

}
