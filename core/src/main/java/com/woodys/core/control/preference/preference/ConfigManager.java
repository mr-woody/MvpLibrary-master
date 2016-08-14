package com.woodys.core.control.preference.preference;

import android.text.TextUtils;

import com.woodys.core.BaseApp;
import com.woodys.core.control.logcat.Logcat;
import com.woodys.core.control.preference.config.Config;
import com.woodys.core.control.preference.config.LayoutInfo;
import com.woodys.core.control.preference.config.LogoConfig;
import com.woodys.core.control.preference.config.NetConfig;
import com.woodys.core.control.preference.reader.AssetReader;
import com.woodys.core.control.preference.reader.CollectReader;
import com.woodys.core.control.preference.reader.LayoutReader;
import com.woodys.core.control.preference.reader.LayoutSnReader;
import com.woodys.core.control.preference.reader.LogoConfigReader;
import com.woodys.core.control.preference.reader.NetInfoReader;
import com.woodys.core.listener.Task;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 配置管理对象
 *
 * @author momo
 * @Date 2014/11/25
 */
public class ConfigManager<K, T extends Config> {
    /**
     * 配置首发logo对象
     */
    private HashMap<String, HashMap<K, T>> mConfigs;

    private static final ConfigManager mConfigManager = new ConfigManager();

    private final HashMap<String, NetConfig> mNetConfigs = new HashMap<>();
    private final HashMap<String, LayoutInfo> mLayoutInfos = new HashMap<>();
    private final HashMap<String, String> mSns = new HashMap<>();

    private ConfigManager() {
        mConfigs = new HashMap<>();
    }

    public static final ConfigManager get() {
        return mConfigManager;
    }

    private HashMap<K, T> ensureConfig(AssetReader<K, T> reader) {
        String name = reader.getClass().getSimpleName();
        HashMap<K, T> configs = mConfigs.get(name);
        if (null == configs) {
            configs = reader.read(BaseApp.getContext());
            mConfigs.put(name, configs);
        }
        return configs;
    }

    public final NetConfig getNetInfo(String action) {
        NetConfig netConfig = mNetConfigs.get(action);
        if (null == netConfig) {
            mNetConfigs.clear();
            HashMap<String, NetConfig> configs = new NetInfoReader().read(BaseApp.getContext());
            if (null != configs && !configs.isEmpty()) {
                mNetConfigs.putAll(configs);
            }
            netConfig = mNetConfigs.get(action);
        }
        return netConfig;
    }

    /**
     * 在子线程中确定取值,执行执定任务
     *
     * @param key
     * @param reader
     * @param task
     */
    public <A extends AssetReader, T extends Config> void runTask(final String key, final A reader, final Task<T> task) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                HashMap<String, T> configs = (HashMap<String, T>) ensureConfig(reader);
                T t = null;
                if (null != configs) {
                    t = configs.get(key);
                }
                subscriber.onNext(t);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
            if (null != task && null != item) {
                task.run(item);
            }
        }, e -> e.printStackTrace());
    }


    /**
     * 获取splash配置项
     *
     * @param channel
     * @return
     */
    public void runLogoAction(String channel, Task<LogoConfig> task) {
        runTask(channel, new LogoConfigReader(), task);
    }

    /**
     * 获取网络配置项
     *
     * @param action
     * @return
     */
    public void runNetAction(String action, Task<T> task) {
        String key = NetInfoReader.class.getSimpleName();
        HashMap<K, T> config = mConfigs.get(key);
        if (null != config) {
            T t = config.get(action);
            if (null != t) {
                if (null != task) {
                    task.run(t);
                }
                return;
            }
        }
        runTask(action, new NetInfoReader(), task);
    }

    /**
     * 获得统计ui信息
     *
     * @param object
     * @return
     */
    public void runUiAction(Object object, Task<T> task) {
        String action = object.getClass().getName();
        String key = CollectReader.class.getSimpleName();
        HashMap<K, T> config = mConfigs.get(key);
        if (null != config) {
            T t = config.get(action);
            if (null != t) {
                if (null != task) {
                    task.run(t);
                }
                return;
            }
        }
        runTask(action, new CollectReader(), task);
    }

    /**
     * @param object
     * @param action1
     */
    public void runLayout(Object object, Action1<LayoutInfo> action1) {
        if (null == object) return;
        String name = object.getClass().getSimpleName();
        LayoutInfo layoutInfo1 = mLayoutInfos.get(name);
        if (null == layoutInfo1) {
            Logcat.e("initLayout");
            Observable.create(new Observable.OnSubscribe<HashMap<String, LayoutInfo>>() {
                @Override
                public void call(Subscriber<? super HashMap<String, LayoutInfo>> subscriber) {
                    HashMap<String, LayoutInfo> layoutInfos = new LayoutReader().read(BaseApp.getContext());
                    subscriber.onNext(layoutInfos);
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io()).subscribe(configs -> {
                if (null != configs) {
                    mLayoutInfos.clear();
                    mLayoutInfos.putAll(configs);
                    Logcat.e("Thread:" + Thread.currentThread().getName());
                    LayoutInfo layoutInfo = configs.get(name);
                    if (null != action1 && null != layoutInfo) {
                        action1.call(layoutInfo);
                    }
                }
            }, e -> e.printStackTrace());
        } else if (null != action1) {
            action1.call(layoutInfo1);
        }
    }

    /**
     * @param object
     * @param action1
     */
    public void runSn(Object object, Action1<String> action1) {
        if (null == object) return;
        String name = object.getClass().getSimpleName();
        String sn = mSns.get(name);
        if (!TextUtils.isEmpty(sn)) {
            Observable.create(new Observable.OnSubscribe<HashMap<String, String>>() {
                @Override
                public void call(Subscriber<? super HashMap<String, String>> subscriber) {
                    HashMap<String, String> layoutInfos = new LayoutSnReader().read(BaseApp.getContext());
                    subscriber.onNext(layoutInfos);
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io()).subscribe(configs -> {
                if (null != configs) {
                    mSns.clear();
                    mSns.putAll(configs);
                    String sn1 = configs.get(name);
                    if (null != action1 && !TextUtils.isEmpty(sn1)) {
                        action1.call(sn1);
                    }
                }
            }, e -> e.printStackTrace());
        } else if (null != action1) {
            action1.call(sn);
        }
    }

    public void recycler() {
    }

}
