package com.woodys.core.model.entity;

import java.util.ArrayList;

/**
 * Created by cz on 16/1/19.
 * 应用配置类
 */
public class DebugConfig {
    public boolean debug;//是否开启debug
    public boolean exception;//是否收集异常
    public boolean themeEnable;//是否开启主题控制
    public boolean netEnable;//是否开启主题控制
    public ArrayList<String> testNet;//测试版本接口

    public DebugConfig() {
        testNet = new ArrayList<>();
    }
}
