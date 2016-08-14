package com.woodys.core.control.anim;
/*
 * Copyright 2014 gitonway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ThumbSlider extends BaseEffect {

    private long s = (mDuration - 200) / 2, m = 200, e = (mDuration - 200) / 2;
    private View iconView;

    public ThumbSlider(View iconView) {
        this.iconView = iconView;
    }

    @Override
    protected void setInAnimation(View view) {
        if (iconView != null && view!=null) {
            ObjectAnimator msgScaleAnim = ObjectAnimator.ofFloat(view, "scaleX", 0, 0.5f, 1, 1.1f, 1).setDuration((int)(s * 2.5));
            ObjectAnimator msgAlphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1).setDuration(s * 2);
            msgScaleAnim.setStartDelay(s + m);
            msgAlphaAnim.setStartDelay(s + m);
            getAnimatorSet().playTogether(
                    ObjectAnimator.ofFloat(iconView, "alpha", 1).setDuration(s),
                    ObjectAnimator.ofFloat(iconView, "scaleX", 0, .5f, 1, 0.9f, 1, 1.2f, 1).setDuration(s),
                    ObjectAnimator.ofFloat(iconView, "scaleY", 0, .5f, 1, 1.2f, 1, 0.9f, 1).setDuration(s),
                    msgScaleAnim,
                    msgAlphaAnim

            );
            getAnimatorSet().addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setVisibility(View.VISIBLE);
                    iconView.setVisibility(View.VISIBLE);
                    ViewHelper.setAlpha(view, 0f);
                    ViewHelper.setPivotX(view, view.getMeasuredWidth());
                    ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
                }
            });
        }
    }

    @Override
    protected void setOutAnimation(View view) {
        if (view!=null) {
            if(iconView!=null){
                ObjectAnimator iconScaleXAnim = ObjectAnimator.ofFloat(iconView, "scaleX", 1, 1.2f, 1, 0.9f, 1, 0.5f, 0).setDuration(e * 2);
                ObjectAnimator iconScaleYAnim = ObjectAnimator.ofFloat(iconView, "scaleY", 1, 0.9f, 1, 1.2f, 1, 0.5f, 0).setDuration(e * 2);
                ObjectAnimator iconAlphaAnim = ObjectAnimator.ofFloat(iconView, "alpha", 1, 0).setDuration(e * 2);

                iconScaleXAnim.setStartDelay(e + m);
                iconScaleYAnim.setStartDelay(e + m);
                iconAlphaAnim.setStartDelay(e + m);
                getAnimatorSet().playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1, 0.5f, 0).setDuration(e),
                        iconScaleXAnim,
                        iconScaleYAnim,
                        iconAlphaAnim

                );
                getAnimatorSet().addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        view.setVisibility(View.VISIBLE);
                        iconView.setVisibility(View.VISIBLE);
                        ViewHelper.setPivotX(view, view.getMeasuredWidth());
                        ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
                    }
                });
            }else{
                getAnimatorSet().play(ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1, 0.5f, 0).setDuration((int)(e * 1.5)));
                getAnimatorSet().addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        view.setVisibility(View.VISIBLE);
                        ViewHelper.setPivotX(view, view.getMeasuredWidth());
                        ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
                    }
                });
            }
        }
    }

    @Override
    protected long getAnimDuration(long duration) {
        return duration * 2 + 200;
    }
}
