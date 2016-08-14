package com.woodys.core.control.util;

import android.content.Context;
import android.content.res.Resources;

import com.woodys.core.BaseApp;

/**
 * 根据资源的名字获取其ID值
 * 
 */
public class ResUtils {

	private static Resources resoutce;
	private static String packageName;
	private static Context context;

	static {
		context = BaseApp.getContext();
	}

	private static final String packageName() {
		if (packageName == null) {
			packageName = context.getPackageName();
		}
		return packageName;
	}

	private static final Resources resources() {
		if (resoutce == null) {
			resoutce = context.getResources();
		}
		return resoutce;
	}

	private static final int identifier(String name, String type) {
		if (resoutce != null) {
			return resoutce.getIdentifier(name, type, packageName());
		}
		return resources().getIdentifier(name, type, packageName());
	}

	public static final int string(String name) {
		return identifier(name, "string");
	}

	public static final int drawable(String name) {
		return identifier(name, "drawable");
	}

	public static final int id(String name) {
		return identifier(name, "id");
	}

	public static final int attr(String name) {
		return identifier(name, "attr");
	}

	public static final int layout(String name) {
		return identifier(name, "layout");
	}

	public static final int menu(String name) {
		return identifier(name, "menu");
	}

	public static final int style(String name) {
		return identifier(name, "style");
	}

	public static int integer(String name) {
		return identifier(name, "integer");
	}

	public static int anim(String name) {
		return identifier(name, "anim");
	}

	public static int raw(String name) {
		return identifier(name, "raw");
	}

	public static int color(String name) {
		return identifier(name, "color");
	}

	public static int array(String name) {
		return identifier(name, "array");
	}

	public static int getItemId(String name) {
		return identifier(name, "id");
	}

	public static int getLayoutId(String name) {
		return identifier(name, "layout");
	}

	public static int getIdentifier(String name, String type) {
		return identifier(name, type);
	}

	public static int getDrawableId(String name) {
		return identifier(name, "drawable");
	}

	public static int getColor(int id) {
		return context.getResources().getColor(id);
	}

	public static String getString(int resId, Object... formatArgs) {
		return context.getString(resId, formatArgs);
	}

}
