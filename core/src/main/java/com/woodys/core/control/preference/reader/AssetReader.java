package com.woodys.core.control.preference.reader;

import android.content.Context;
import android.content.res.Resources;

import com.woodys.core.control.util.IOUtils;
import com.woodys.core.control.util.ResUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public abstract class AssetReader<K, T> {

    /**
     * 初始化资源
     */
    public HashMap<K, T> read(Context context) {
        InputStream inputStream = null;
        HashMap<K, T> configs = null;
        try {
            Resources appResource = context.getResources();
            configs = new HashMap<>();
            Config config = getClass().getAnnotation(Config.class);
            if (null != config) {
                String[] values = config.value();
                if (0 < values.length) {
                    if (0 == config.type()) {
                        inputStream = appResource.getAssets().open(values[0]);
                    } else if (1 == config.type()) {
                        inputStream = appResource.openRawResource(ResUtils.raw(values[0]));
                    }
                    if (null != inputStream) {
                        XmlParser.ParserListener listener = getParserListener(configs);
                        if (null != listener) {
                            XmlParser.startParser(inputStream, listener);
                        } else {
                            throw new NullPointerException("listener 不能为空!");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }
        return configs;
    }

    /**
     * 解析attr
     *
     * @param parser
     * @param listener
     */
    protected void parserAttrs(XmlPullParser parser, LayoutReader.XmlLayoutParserListener listener) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String namespace = parser.getAttributeNamespace(i);
            if (null != listener) {
                listener.parser(namespace, parser.getAttributeName(i), parser.getAttributeValue(i));
            }
        }
    }

    /**
     * 读取配置项
     *
     * @return
     */
    public abstract XmlParser.ParserListener getParserListener(HashMap<K, T> configs);

    public interface XmlLayoutParserListener {
        void parser(String nameSpace, String attrName, String attrValue);
    }
}
