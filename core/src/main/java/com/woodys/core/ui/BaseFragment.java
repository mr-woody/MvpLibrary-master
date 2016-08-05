package com.woodys.core.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woodys.core.model.mvp.BasePresenter;
import com.woodys.core.model.mvp.BaseView;
import com.woodys.core.model.mvp.LogicProxy;
import com.woodys.core.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author: woodys
 * @date: 2016-05-26 17:19
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    private LoadingView mLoginView;
    protected BasePresenter mPresenter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mLoginView = new LoadingView(getActivity());
        onInitView();
        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    public void showLoadingView() {
        mLoginView.show();
    }

    public void hideLoadingView() {
        mLoginView.hide();
    }

    //获得该页面的实例
    public <T> T getLogicImpl(Class cls, BaseView o) {
        return LogicProxy.getInstance().bind(cls, o);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
        if (mPresenter != null)
            mPresenter.detachView();
    }

}
