package com.woodys.core.model.mvp;

/**
 * @author: woodys
 * @date: 2016-06-21 17:42

 */
public interface Presenter<V> {
    void attachView(V mvpView);
    void detachView();
}
