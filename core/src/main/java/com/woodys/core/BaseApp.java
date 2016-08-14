package com.woodys.core;

import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.woodys.core.base.util.SpUtil;
import com.woodys.core.control.logcat.LogLevel;
import com.woodys.core.control.logcat.Logcat;
import com.woodys.core.control.preference.reader.AppConfigReader;
import com.woodys.core.model.entity.DebugConfig;

/**
 * Created by baixiaokang on 16/4/23.
 */
public class BaseApp extends MultiDexApplication {
    private static BaseApp mApp;

    private static DebugConfig mConfig;
    private static long mStartTime;//开启时间

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        Logcat.init("com.android.logcat")
                .methodCount(0)
//                .logLevel(LogLevel.FULL)
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .hideThreadInfo();

        SpUtil.init(this);
    }

    public static Context getContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }


    public static DebugConfig getAppConfig() {
        if (null == mConfig) {
            mConfig = new AppConfigReader().read(BaseApp.getContext());
        }
        return mConfig;
    }

    /**
     * 设置app打开时间
     */
    public static void setStartTime() {
        mStartTime = System.currentTimeMillis() / 1000;
    }

    /**
     * 获得app使用时间
     */
    public static long getUseTime() {
        if (0 == mStartTime) {
            mStartTime = System.currentTimeMillis() / 1000;
        }
        return System.currentTimeMillis() / 1000 - mStartTime;
    }

    /**
     * 获得app使用时间
     */
    public static long getUseTimeMillis() {
        if (0 == mStartTime) {
            mStartTime = System.currentTimeMillis() / 1000;
        }
        return System.currentTimeMillis() - mStartTime * 1000;
    }

}
