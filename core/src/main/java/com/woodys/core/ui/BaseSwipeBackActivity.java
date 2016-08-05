package com.woodys.core.ui;

import android.os.Bundle;
import android.view.View;

import com.woodys.core.widget.swipeback.SwipeBackLayout;
import com.woodys.core.widget.swipeback.Utils;
import com.woodys.core.widget.swipeback.app.SwipeBackActivityBase;
import com.woodys.core.widget.swipeback.app.SwipeBackActivityHelper;


/**
 * Created by fangxiao on 15/12/18.
 */
public abstract class BaseSwipeBackActivity extends BaseAppActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}