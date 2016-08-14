package com.woodys.core.control.anim;

/**
 * view扩展属性
 * 
 * @author momo
 * @Date 2014/11/20
 * 
 */
/**
 * @author Administrator
 * 
 * @param <V>
 */
public class AnimValue<V> {
	private int width, height;
	private int alpha;
	private V v;

	public AnimValue(V v) {
		this.v = v;
	}

	public AnimValue() {
		super();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "width:" + width + " height:" + height + " alpha:" + alpha;
	}

}
