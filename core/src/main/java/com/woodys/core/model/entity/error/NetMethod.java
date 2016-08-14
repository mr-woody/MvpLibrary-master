package com.woodys.core.model.entity.error;

import android.support.annotation.StringDef;

/**
 * Created by cz on 15/9/21.
 * 网络请求类型
 */
public interface NetMethod {
    String GET = "get";
    String POST = "post";

    @StringDef(value = {GET, POST})
    @interface Method {
    }
}
