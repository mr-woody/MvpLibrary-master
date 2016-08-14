package com.woodys.core.control.preference.reader;

import android.content.Context;
import android.content.res.Resources;

import com.woodys.core.control.util.IOUtils;
import com.woodys.core.model.entity.DebugConfig;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by cz on 16/1/19.
 * 应用配置信息
 */
public class AppConfigReader {

    private DebugConfig debugConfig;

    public DebugConfig read(Context context) {
        InputStream inputStream = null;
        try {
            Resources appResource = context.getResources();
            inputStream = appResource.getAssets().open("config/app_config.xml");
            if (null != inputStream) {
                debugConfig = new DebugConfig();
                Class<? extends DebugConfig> clazz = debugConfig.getClass();
                XmlParser.startParser(inputStream, new XmlParser.ParserListener() {
                    @Override
                    public void startParser(XmlPullParser parser) {
                        String name = parser.getName();
                        if ("config_item".equals(name)) {
                            String fieldName = parser.getAttributeValue(0);
                            String fieldValue = parser.getAttributeValue(1);
                            try {
                                Field field = clazz.getField(fieldName);
                                field.set(debugConfig, Boolean.valueOf(fieldValue));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if ("net".equals(name)) {
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeName = parser.getAttributeName(i);
                                String attributeValue = parser.getAttributeValue(i);
                                if ("enable".equals(attributeName)) {
                                    debugConfig.netEnable = Boolean.valueOf(attributeValue);
                                }
                            }
                        } else if ("net_item".equals(name)) {
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                String attributeName = parser.getAttributeName(i);
                                String attributeValue = parser.getAttributeValue(i);
                                if ("name".equals(attributeName)) {
                                    debugConfig.testNet.add(attributeValue);
                                }
                            }
                        }
                    }

                    @Override
                    public void endParser(XmlPullParser parser) {
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(inputStream);
        }
        return debugConfig;
    }
}
