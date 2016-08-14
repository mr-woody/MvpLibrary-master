package com.woodys.core.listener;

/**
 * 当前任务
 * 
 * @author momo
 * @param <T>
 * @Date 2014/10/17
*/
public interface Task<T> {
	void run(T t);
}
