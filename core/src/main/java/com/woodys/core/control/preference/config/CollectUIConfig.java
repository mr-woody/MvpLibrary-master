package com.woodys.core.control.preference.config;

import java.util.HashMap;

/**
 * 界面统计元素对象
 * 
 * @author momo
 * @Date 2014/1/17
 */
public class CollectUIConfig implements Config{
	public String className;// 类路径
	public String name;// 名称
	public boolean isExclude;// 不计于统计内
	public HashMap<String, String> items;

	public CollectUIConfig() {
		super();
		items = new HashMap<String, String>();
	}
}
