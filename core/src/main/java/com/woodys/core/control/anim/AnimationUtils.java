package com.woodys.core.control.anim;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.woodys.core.R;
import com.woodys.core.listener.ITask;

/**
 * 管理动画显示
 *
 * @author momo
 * @Date 2014/6/5
 */
public class AnimationUtils {
    public static final int VIEW_ANIMATION_DURATION = 800;
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(R.color.transparent);

    /**
     * 获得渐变透明度动画效果
     */
    public static void startAlphaAnimation(View view, float startAlpha, float endAlpha) {
        startAlphaAnimation(view, startAlpha, endAlpha, 500);
    }

    public static void startAlphaAnimation(View view, float startAlpha, float endAlpha, long duration) {
        AlphaAnimation alpha = new AlphaAnimation(startAlpha, endAlpha);
        alpha.setDuration(duration);
        alpha.setFillAfter(true);
        view.startAnimation(alpha);
    }

    /**
     * 获得缩放扩大的动画
     */
    public static void startScaleAnimation(View view, float fromX, float toX, float fromY, float toY) {
        ScaleAnimation scale = new ScaleAnimation(fromX, toX, fromY, toY);
        scale.setDuration(3000);
        view.startAnimation(scale);
    }

    /**
     * 获得画面位置移动动画的效果
     */
    public static void startTranslateAnimation(View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        TranslateAnimation translate = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translate.setDuration(2000);
        view.startAnimation(translate);
    }

    public static void startTranslateAnimation(View view, float fromX, float toX, float fromY, float toY, long duration) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, fromY, toY);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /**
     * 设置自身轨迹移动动画
     *
     * @param view
     * @param fromXType
     * @param fromXValue
     * @param toXType
     * @param toXValue
     * @param fromYType
     * @param fromYValue
     * @param toYType
     * @param toYValue
     */
    public void startTranslateAnimation(View view, int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue, long duration) {
        TranslateAnimation animation = new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);
    }

    /**
     * 获得画面旋转动画的效果
     */
    public static void startRotateAnimation(View view, float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        view.startAnimation(rotate);
    }

    /**
     * 获得指定控件位置面画旋转的动画
     */
    public static void startRotateAnimation(View view, float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long duration) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotate.setDuration(duration);
        rotate.setInterpolator(new LinearInterpolator());
        // 设置动画循环播放
        rotate.setRepeatCount(Animation.INFINITE);
        view.startAnimation(rotate);
    }

    /**
     * 开始动画
     *
     * @param view        : 开始动画控件
     * @param resId       : 动画资源
     * @param r           :执行事件
     * @param delayMillis :
     */
    public static void startAnimation(Context context, View view, int resId, Runnable r, long delayMillis) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(context, resId);
        view.startAnimation(animation);
        // 执行其他事件
        if (null != r) {
            new Handler().postDelayed(r, delayMillis);
        }
    }

    /**
     * 开启位图动画
     */
    public static void setTransitionDrawable(ImageView view, int color, int duration) {
        final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE, new ColorDrawable(color)});
        view.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(duration);
    }

    public static void setTransitionDrawable(View view, int color, int duration) {
        final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE, new ColorDrawable(color)});
        view.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(duration);
    }

    /**
     * 开始背景动画
     */
    public static void setBackGroundAnim(View view, int duration, int... colors) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", colors);
        colorAnim.setDuration(duration);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }

    /**
     * 开始view内image重绘动画
     */
    public static void startViewImageAnim(final View view) {
        final Drawable drawable = getViewDrawable(view);
        if (null != drawable) {
            final int bitmapWidth = drawable.getIntrinsicWidth();
            final int bitmapHeight = drawable.getIntrinsicHeight();

            ValueAnimator squashAnimSize = ObjectAnimator.ofInt(3);
            squashAnimSize.setDuration(VIEW_ANIMATION_DURATION / 4);
            squashAnimSize.setRepeatCount(1);
            squashAnimSize.setRepeatMode(ValueAnimator.REVERSE);
            squashAnimSize.setInterpolator(new DecelerateInterpolator());
            final View drawView = getDrawView(view);
            squashAnimSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int size = Integer.valueOf(animation.getAnimatedValue().toString());
                    Rect rect = new Rect(size, size, bitmapWidth - size, bitmapHeight - size);
                    drawable.setBounds(rect);
                    drawView.invalidate();
                }
            });
            squashAnimSize.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Rect rect = new Rect(0, 0, bitmapWidth, bitmapHeight);
                    drawable.setBounds(rect);
                    drawView.invalidate();
                }
            });
            squashAnimSize.start();
        }
    }

    private static View getDrawView(View view) {
        Drawable drawable = null;
        if (view instanceof ViewGroup) {
            ViewGroup gounp = (ViewGroup) view;
            for (int i = 0; i < gounp.getChildCount(); i++) {
                drawable = getChildViewDrawable(gounp.getChildAt(i));
                if (null != drawable) {
                    view = gounp.getChildAt(i);
                    break;
                }
            }
        }
        return view;
    }

    private static Drawable getViewDrawable(View view) {
        Drawable drawable = null;
        if (view instanceof ViewGroup) {
            ViewGroup gounp = (ViewGroup) view;
            for (int i = 0; i < gounp.getChildCount(); ) {
                drawable = getChildViewDrawable(gounp.getChildAt(i++));
                if (null != drawable) {
                    break;
                }
            }
        } else {
            drawable = getChildViewDrawable(view);
        }
        return drawable;
    }

    private static Drawable getChildViewDrawable(final View view) {
        Drawable drawable = null;
        if (view instanceof TextView) {
            Drawable[] drawables = ((TextView) view).getCompoundDrawables();
            for (int i = 0; i < drawables.length; i++) {
                if (null != drawables[i]) {
                    drawable = drawables[i];
                    break;
                }
            }
//			if (null == drawable) {
//				drawable = view.getBackground();
//			}
        } else if (view instanceof ImageView) {
            drawable = ((ImageView) view).getDrawable();
        } /*else {
            drawable = view.getBackground();
		}*/
        return drawable;
    }

    /**
     * 执行颜色变幻
     *
     * @param textView
     * @param startColor
     * @param endColor
     */
    public static void startColorAnim(final TextView textView, final int startColor, final int endColor) {
        ValueAnimator colorAnim = ObjectAnimator.ofFloat(1f);
        colorAnim.setDuration(300);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor(evaluate(animator.getAnimatedFraction(), startColor, endColor));
            }
        });
        colorAnim.start();
    }

    public static void startImageAnim(View view) {
        Keyframe kf0 = Keyframe.ofFloat(0.2f, 360);
        Keyframe kf1 = Keyframe.ofFloat(0.5f, 30);
        Keyframe kf2 = Keyframe.ofFloat(0.8f, 1080);
        Keyframe kf3 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2, kf3);
        PropertyValuesHolder scale = PropertyValuesHolder.ofKeyframe("scale", kf0, kf1, kf2, kf3);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.play(ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation)).with(ObjectAnimator.ofPropertyValuesHolder(view, scale));
        animationSet.setDuration(2000);
        animationSet.start();
    }

    public static int evaluate(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24);
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24);
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) | (int) ((startR + (int) (fraction * (endR - startR))) << 16) | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }

    public static void setValueWithAnim(final ITask<String> task, int toValue) {
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(toValue);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (null != task) {
                    task.run(valueAnimator.getAnimatedValue().toString());
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 取值变化动画
     *
     * @param view
     * @param value
     * @param targetValue
     */
    public static void startIntValueAnim(Context context,final TextView view, final int res, final int value, int targetValue, long duration) {
        AnimValue<Integer> animValue = new AnimValue<>(value);
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(animValue, "v", targetValue);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = Integer.valueOf(valueAnimator.getAnimatedValue().toString());
                if (-1 == res) {
                    view.setText(String.valueOf(value));
                } else {
                    view.setText(context.getString(res, value));
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 更改view取值
     * 一段测试代码,未经过测试勿用
     *
     * @param view
     * @param value
     * @param targetValue
     */
    public static void startViewValueAnim(final View view, final int value, int targetValue, long duration) {
        AnimValue<Integer> animValue = new AnimValue<>(value);
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(animValue, "v", targetValue);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = Integer.valueOf(valueAnimator.getAnimatedValue().toString());
                view.requestLayout();
            }
        });
        valueAnimator.start();
    }

    /**
     * 控件抖动
     *
     * @param view
     */
    public static void startShake(Context context,View view) {
        Animation shake = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.shake);//加载动画资源文件
        view.startAnimation(shake); //给组件播放动画效果
    }
}
