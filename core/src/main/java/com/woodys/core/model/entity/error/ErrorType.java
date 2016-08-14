package com.woodys.core.model.entity.error;

import android.support.annotation.IntDef;

/**
 * Created by cz on 15/9/21.
 * 错误类型
 */
public interface ErrorType {
    //    异常类型;1:无网络,2:解析异常:3:网络超时,4:返回数据异常,5:应用崩溃,6:程序片断异常,7:日志记录
    int NO_NETWORK = 1;
    int JSON_ERROR = 2;
    int TIME_OUT = 3;
    int SERVER_ERROR = 4;
    int APP_ERROR = 5;
    int ACTION_ERROR = 6;
    int LOG = 7;

    @IntDef(value = {NO_NETWORK, JSON_ERROR, TIME_OUT, SERVER_ERROR, APP_ERROR, ACTION_ERROR, LOG})
    @interface Type {
    }
}
