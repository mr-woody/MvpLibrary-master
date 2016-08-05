package com.woodys.core.control;

import android.support.v4.view.PagerAdapter;

/**
 * @author: woodys
 * @date: 2016-05-31 10:56

 */
public interface CustomInterface {
    void updateIndicatorView(int size, int resid);

    void setAdapter(PagerAdapter adapter);

    void startScorll();

    void endScorll();
}
