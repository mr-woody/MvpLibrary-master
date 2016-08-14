package com.woodys.core.control.preference.reader;


import com.woodys.core.control.preference.config.LogoConfig;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * 展示图标首发配置项
 *
 * @author momo
 * @Date 2014/11/25
 */
@Config("config/logo_config.xml")
public class LogoConfigReader extends AssetReader<String, LogoConfig> {

    @Override
    public XmlParser.ParserListener getParserListener(final HashMap<String, LogoConfig> configs) {
        return new XmlParser.ParserListener() {
            private LogoConfig config;

            @Override
            public void startParser(XmlPullParser parser) {
                if ("item".equals(parser.getName())) {
                    config = new LogoConfig();
                    // 记录主题名称id
                    XmlParser.runParser(parser, (String... ts) -> {
                        if ("channel".equals(ts[0])) {
                            config.channel = ts[1];
                        } else if ("info".equals(ts[0])) {
                            config.info = ts[1];
                        } else if ("status".equals(ts[0])) {
                            config.status = Boolean.valueOf(ts[1]);
                        } else if ("res".equals(ts[0])) {
                            config.res = ts[1];
                        }
                    });
                }
            }

            @Override
            public void endParser(XmlPullParser parser) {
                if ("item".equals(parser.getName()) && null != config) {
                    configs.put(config.channel, config);
                }
            }
        };
    }
}
