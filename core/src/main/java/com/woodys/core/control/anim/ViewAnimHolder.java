package com.woodys.core.control.anim;

import android.view.View;

import java.util.WeakHashMap;

/**
 * view properti扩展属性集
 * 
 * @author momo
 * @Date 2014/11/18
 * 
 */
public class ViewAnimHolder {
	private static final WeakHashMap<View, AnimValue<Void>> animViews = new WeakHashMap<View, AnimValue<Void>>();

	public static AnimValue<Void> getAnimValue(View view) {
		AnimValue<Void> animValue = animViews.get(view);
		if (null == animValue) {
			animValue = new AnimValue<Void>();
			animViews.put(view, animValue);
		}
		return animValue;
	}

	public static void remove(View view) {
		animViews.remove(view);
	}
}
