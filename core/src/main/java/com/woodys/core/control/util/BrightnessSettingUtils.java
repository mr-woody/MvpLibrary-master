package com.woodys.core.control.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.Window;
import android.view.WindowManager;

/**
 * 调整系统亮度工具类
 * 
 * @author Administrator
 * 
 */
public class BrightnessSettingUtils {

	private static void initBrightnessSetting(Activity activity) {
		int brightness = 100;
		int mode = 0;
		try {
			brightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		try {
			mode = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		int sOriginalBrightness = brightness;
		int sOriginalMode = mode;
		// Settings.System.putInt(activity.getContentResolver(),
		// Settings.System.SCREEN_BRIGHTNESS_MODE, systemMode);
		// Settings.System.putInt(activity.getContentResolver(),
		// Settings.System.SCREEN_BRIGHTNESS, systemBrightness);

		Window window = activity.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.screenBrightness = brightness;
		window.setAttributes(lp);
	}

	/**
	 * 判断是否开启了自动亮度调节
	 * 
	 * @param aContext
	 * @return
	 */
	public static boolean isAutoBrightness(Activity activity) {
		ContentResolver aContentResolver = activity.getContentResolver();
		boolean automicBrightness = false;
		try {
			automicBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		return automicBrightness;
	}

	/**
	 * 获取屏幕的亮度
	 * 
	 * @param activity
	 * @return 返回的值最大255.
	 */
	public static int getScreenBrightness(Activity activity) {
		int nowBrightnessValue = 0;
		ContentResolver resolver = activity.getContentResolver();
		try {
			nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nowBrightnessValue;
	}

	/**
	 * 设置亮度
	 *
	 * @param activity
	 * @param brightness
	 */
	public static void setScreenBrightness(Activity activity, int brightness) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 停止自动亮度调节
	 *
	 * @param activity
	 */
	public static void stopAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	/**
	 * 开启亮度自动调节
	 *
	 * @param activity
	 */
	public static void startAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}

	/**
	 * 保存亮度设置状态
	 *
	 * @param resolver
	 * @param brightness
	 */
	public static void saveBrightness(Context context, int brightness) {
		if (null != context) {
			ContentResolver resolver = context.getContentResolver();
			Uri uri = Settings.System.getUriFor("screen_brightness");
			Settings.System.putInt(resolver, "screen_brightness", brightness);
			// resolver.registerContentObserver(uri, true, myContentObserver);
			resolver.notifyChange(uri, null);
		}
	}
}
