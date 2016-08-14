package com.woodys.core.control.util;

/**
 * 多次点击事件
 *
 * @author momo
 * @Date 2014/6/3
 */
public abstract class NClick<T> {
    /**
     * 点击次数
     */
    private long[] clickCount;
    /**
     * 多击间隔时间
     */
    private long interval;

    public NClick(int n, long interval) {
        clickCount = new long[n];
        this.interval = interval;
    }

    /**
     * 默认300毫秒的点击
     *
     * @param n
     */
    public NClick(int n) {
        clickCount = new long[n];
        this.interval = 300;
    }

    /**
     * 默认为300毫秒的双击
     */
    public NClick() {
        clickCount = new long[2];
        this.interval = 300;
    }

    /**
     * 点击成功后执行的方法
     */
    protected abstract void toDo(T... ts);

    public void noToDo() {
    }

    /**
     * 多次点击事件
     */
    public void nClick(T... ts) {
        int length = clickCount.length - 1;
        System.arraycopy(clickCount, 1, clickCount, 0, length);
        clickCount[length] = System.currentTimeMillis();
        if (clickCount[length] - clickCount[0] < interval) {
            //重置所有状态
            for (int i = 0; i < clickCount.length; i++) {
                clickCount[i] = 0;
            }
            toDo(ts);
        } else {
            noToDo();
        }
    }
}
