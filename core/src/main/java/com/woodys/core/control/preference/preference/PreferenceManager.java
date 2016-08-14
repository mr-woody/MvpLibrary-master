package com.woodys.core.control.preference.preference;

import android.os.Environment;

import java.io.File;

/**
 * 配置项管理
 *
 * @author momo
 */
public class PreferenceManager {
    public static final float DEFAULT_COMPRESS_WIDTH = 600;// 默认压缩的图片宽
    public static final boolean SHOW_UMENG_FEATURE = false;// 显示友盟购物把手
    public static final int LIGHT_VIEW = 0;// 白天视图
    public static final int NIGHT_VIEW = 1;// 夜晚视图
    public static final File appFile;// 程序SD卡目录
    public static final File imageCache;// 图片缓存
    public static final File voiceFile;// 语音缓存
    public static final File downImage;// 下载本地图片目录
    public static final File downFile;// 下载本地文件目录
    public static final File tempFile;// 临时文件
    public static final File logFile;// 日志文件
    public static final File QRFile;//二维码文件
    public static final File offlineFile;// 离线缓存
    public static final File netLog;
    public static final File requestLog;

    static {
        appFile = new File(Environment.getExternalStorageDirectory(), "/wx看点/");
        imageCache = new File(appFile, "/cache/");
        downImage = new File(appFile, "/image/");
        voiceFile = new File(appFile, "/voice/");
        downFile = new File(appFile, "/down/");
        tempFile = new File(imageCache, "temp.jpg");
        logFile = new File(appFile, "/log/");
        offlineFile = new File(appFile, "/offline/");
        mkdirs(appFile, downImage, downFile, imageCache, voiceFile, logFile, offlineFile);
        QRFile = new File(downImage, "qr.jpg");
        netLog = new File(logFile, "net.txt");
        requestLog = new File(logFile, "request.txt");
    }

    public static void mkdirs(File... files) {
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                if (!files[i].exists()) {
                    files[i].mkdir();
                }
            }
        }
    }

}
