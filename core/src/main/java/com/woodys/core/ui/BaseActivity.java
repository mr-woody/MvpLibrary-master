package com.woodys.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.woodys.core.control.logcat.Logcat;
import com.woodys.core.model.mvp.BasePresenter;
import com.woodys.core.model.mvp.BaseView;
import com.woodys.core.model.mvp.LogicProxy;
import com.woodys.core.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author: woodys
 * @date: 2016-05-26 17:17
 */
public abstract class BaseActivity extends Activity {

    private LoadingView mLoadingView;
    protected BasePresenter mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logcat.d("Activity Location (%s.java:0)", getClass().getSimpleName());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);
        mLoadingView = new LoadingView(this);
        onInitView();
    }

    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // 打开Activity动画
    }

    public void showLoadView() {
        mLoadingView.show();
    }

    public void hideLoadView() {
        mLoadingView.hide();
    }

    //获得该页面的实例
    public <T> T getLogicImpl(Class cls, BaseView o) {
        return LogicProxy.getInstance().bind(cls, o);
    }

    @Override
    public void finish() {
        super.finish();
        // 关闭动画
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
        if (mPresenter != null)
            mPresenter.detachView();
    }

}
