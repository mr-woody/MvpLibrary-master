package com.woodys.core.control.anim;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * 控件属性控件对象
 * 
 * @author momo
 * @Date 2014/11/19
 * 
 */
public class LayoutParamsHolder {
	private final LayoutParams params;
	private final View view;

	public LayoutParamsHolder(View view, LayoutParams params) {
		super();
		this.view = view;
		this.params = params;
	}

	public int getWidth() {
		return params.width;
	}

	public void setWidth(int width) {
		params.width = width;
		view.setLayoutParams(params);
	}

	public int getHeight() {
		return params.height;
	}

	public void setHeight(int height) {
		params.height = height;
		view.setLayoutParams(params);
	}

}
