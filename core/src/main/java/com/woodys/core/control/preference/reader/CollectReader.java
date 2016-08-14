package com.woodys.core.control.preference.reader;


import com.woodys.core.control.preference.config.CollectUIConfig;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * 统计信息读取
 *
 * @author momo
 * @Date 2015/1/17
 */
@Config("config/collect_config.xml")
public class CollectReader extends AssetReader<String, CollectUIConfig> {
    @Override
    public XmlParser.ParserListener getParserListener(final HashMap<String, CollectUIConfig> configs) {
        return new XmlParser.ParserListener() {
            private CollectUIConfig config;
            private String catid;
            private String name;

            @Override
            public void startParser(XmlPullParser parser) {
                if ("item".equals(parser.getName())) {
                    config = new CollectUIConfig();
                    XmlParser.runParser(parser, (String... ts) -> {
                        if ("class".equals(ts[0])) {
                            config.className = ts[1];
                        } else if ("name".equals(ts[0])) {
                            config.name = ts[1];
                        } else if ("exclude".equals(ts[0])) {
                            config.isExclude = Boolean.parseBoolean(ts[1]);
                        }
                    });
                } else if ("child".equals(parser.getName())) {
                    XmlParser.runParser(parser, (String... ts) -> {
                        if ("catid".equals(ts[0])) {
                            catid = ts[1];
                        } else if ("name".equals(ts[0])) {
                            name = ts[1];
                        }
                    });
                }
            }

            @Override
            public void endParser(XmlPullParser parser) {
                if ("item".equals(parser.getName())) {
                    configs.put(config.className, config);
                } else if ("child".equals(parser.getName())) {
                    config.items.put(catid, name);
                }
            }
        };
    }

}
