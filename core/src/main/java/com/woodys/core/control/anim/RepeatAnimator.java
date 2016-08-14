package com.woodys.core.control.anim;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.woodys.core.control.logcat.Logcat;

import java.util.ArrayList;

/**
 * Created by cz on 15/6/10.
 * 自定义重复动画动画
 */
public class RepeatAnimator extends Animator implements Handler.Callback {
    private static final String TAG = RepeatAnimator.class.getCanonicalName();
    private static final int FRAME_TIME = 25;//每桢时间
    private static final int DEFAULT_DURATION = 300;//动画执行默认时间
    private static final int ANIMATION_START = 0;
    private static final int ANIMATION_FRAME = 1;
    private static final int ANIMATION_CANCEL = 2;
    private ArrayList<AnimatorListener> mListeners;
    private ArrayList<AnimationUpdateListener> mUpdateListeners;
    private long mDuration;//动画执行时间
    private long mStartDelay;//动画执行延持时间
    private float mFloatValue;//float取值
    private float mFloatCurrentValue;//动画执行Int值
    private int mIntValue;//int取值
    private int mIntCurrentValues;//动画执行int值
    private float mFraction;//当前执行率0f-1f
    private long mCurrentTime;
    private long mCurrentStartTime;
    private boolean isRunning;//当前动行是否执行
    private boolean isCancel;//是否结束

    private Handler mHandler;

    /**
     * Instantiates a new Repeat animator.
     */
    public RepeatAnimator() {
        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public long getStartDelay() {
        return mStartDelay;
    }

    public void setStartDelay(long startDelay) {
        this.mStartDelay = startDelay;
    }

    public RepeatAnimator setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    public long getDuration() {
        return mDuration;
    }

    public int getIntValue() {
        return mIntCurrentValues;
    }

    public float getFloatValue() {
        return mFloatCurrentValue;
    }

    @Override
    public void setInterpolator(Interpolator value) {
    }


    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Of int.
     *
     * @param intValue the int value
     * @return the repeat animator
     */
    public static RepeatAnimator ofInt(int intValue) {
        RepeatAnimator anim = new RepeatAnimator();
        anim.setIntValue(intValue);
        return anim;
    }

    private void setIntValue(int intValue) {
        this.mIntValue = intValue;
    }

    /**
     * Of flaot.
     *
     * @param flaotValue the flaot value
     * @return the repeat animator
     */
    public static RepeatAnimator ofFlaot(float flaotValue) {
        RepeatAnimator anim = new RepeatAnimator();
        anim.setFloatValue(flaotValue);
        return anim;
    }

    private void setFloatValue(float floatValue) {
        this.mFloatValue = floatValue;
    }

    public void start() {
        if (!isRunning) {
            isCancel = false;
            resetAnimatorData();
            mHandler.sendEmptyMessageDelayed(ANIMATION_START, mStartDelay);
        }
    }

    public void cancel() {
        isCancel = true;
        isRunning = false;
        resetAnimatorData();
        mHandler.sendEmptyMessage(ANIMATION_CANCEL);
        Logcat.i(TAG, "cancel_method:" + isCancel);
        if (null != mListeners) {
            int size = mListeners.size();
            for (int i = 0; i < size; i++) {
                mListeners.get(i).onAnimationCancel(this);
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ANIMATION_START:
                isCancel = false;
                isRunning = true;
                //通知动画执行
                if (null != mListeners) {
                    int size = mListeners.size();
                    for (int i = 0; i < size; i++) {
                        mListeners.get(i).onAnimationStart(this);
                    }
                }
            case ANIMATION_FRAME:
                mCurrentTime += FRAME_TIME;
                mFraction = mCurrentTime * 1.0f / mDuration;

                if (0 != mIntValue) {
                    mIntCurrentValues = (int) (mIntValue * mFraction);
                } else if (0 != mFloatValue) {
                    mFloatCurrentValue = mFloatValue * mFraction;
                }
                long currentTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                if (!isCancel) {
                    Logcat.i(TAG, "cancel:" + isCancel);
                    if (null != mUpdateListeners) {
                        int size = mUpdateListeners.size();
                        for (int i = 0; i < size; i++) {
                            mUpdateListeners.get(i).onUpdate(this);
                        }
                    }
                    if (mCurrentTime < mDuration) {
                        mHandler.sendEmptyMessageDelayed(ANIMATION_FRAME, Math.max(FRAME_TIME, FRAME_TIME - currentTime - mCurrentStartTime));
                    } else {
                        //重复执行
                        resetAnimatorData();
                        if (null != mListeners) {
                            int size = mListeners.size();
                            for (int i = 0; i < size; i++) {
                                mListeners.get(i).onAnimationRepeat(this);
                            }
                        }
                        mHandler.sendEmptyMessageDelayed(ANIMATION_FRAME, Math.max(FRAME_TIME, FRAME_TIME - currentTime - mCurrentStartTime));
                    }
                    mCurrentStartTime = currentTime;
                }
                break;
            case ANIMATION_CANCEL:
                isCancel = true;
                isRunning = false;
                break;
        }
        return false;
    }

    private void resetAnimatorData() {
        mFraction = 0;
        mCurrentTime = 0;
        mIntCurrentValues = 0;
        mFloatCurrentValue = 0;
    }

    public void addListener(AnimatorListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<AnimatorListener>();
        }
        mListeners.add(listener);
    }

    public void removeListener(AnimatorListener listener) {
        if (mListeners == null) {
            return;
        }
        mListeners.remove(listener);
        if (mListeners.size() == 0) {
            mListeners = null;
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return mListeners;
    }

    public void removeAllListeners() {
        if (mListeners != null) {
            mListeners.clear();
            mListeners = null;
        }
    }


    public void addUpdateListener(AnimationUpdateListener listener) {
        if (mUpdateListeners == null) {
            mUpdateListeners = new ArrayList<AnimationUpdateListener>();
        }
        mUpdateListeners.add(listener);
    }

    public void removeUpdateListener(AnimationUpdateListener listener) {
        if (mUpdateListeners == null) {
            return;
        }
        mUpdateListeners.remove(listener);
        if (mUpdateListeners.isEmpty()) {
            mUpdateListeners = null;
        }
    }

    public ArrayList<AnimationUpdateListener> getUpdateListeners() {
        return mUpdateListeners;
    }

    public void removeAllUpdateListeners() {
        if (null != mUpdateListeners) {
            mUpdateListeners.clear();
            mUpdateListeners = null;
        }
    }

    @Override
    public RepeatAnimator clone() {
        final RepeatAnimator anim = (RepeatAnimator) super.clone();
        if (mListeners != null) {
            ArrayList<AnimatorListener> oldListeners = mListeners;
            anim.mListeners = new ArrayList<>();
            int numListeners = oldListeners.size();
            for (int i = 0; i < numListeners; ++i) {
                anim.mListeners.add(oldListeners.get(i));
            }
        }
        return anim;
    }

    /**
     * 动画刷新监听
     */
    public interface AnimationUpdateListener {
        void onUpdate(RepeatAnimator animator);
    }
}
