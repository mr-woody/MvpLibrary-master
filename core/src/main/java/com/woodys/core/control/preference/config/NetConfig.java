package com.woodys.core.control.preference.config;

/**
 * 网络信息管理对象
 *
 * @author momo
 * @Date 2014/11/26
 */
public class NetConfig implements Config {
    public String action;// 请求方法
    public String method;// 请求get/post
    public String info;
    public String[] params;// 请求参数
    public String url;// 请求url前缀
    public String tag;//请求标签
    public boolean filter;//非空过滤
    public boolean addExtras;//添加附加数据,部分接口不需要.例授权
    public String[] files;//提交上传文件
    public boolean replease;//附加数据与现在数据重复时,是否替换
    public boolean execute;//是异步子线程运行回调方法
    public boolean isEncrypt;//请求参数是否加密
    public boolean outside;//是否是第三方接口
    public int version;//接口版本号

    public NetConfig() {
        this.filter = true;
        this.addExtras = true;
        this.replease = true;
        this.outside = false;
    }
}

