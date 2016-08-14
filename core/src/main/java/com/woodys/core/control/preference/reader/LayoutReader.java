package com.woodys.core.control.preference.reader;


import com.woodys.core.control.preference.config.LayoutInfo;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * Created by cz on 15/12/14.
 */
@Config(value = "layout", type = 1)
public class LayoutReader extends AssetReader<String, LayoutInfo> {
    @Override
    public XmlParser.ParserListener getParserListener(HashMap<String, LayoutInfo> configs) {
        return new XmlParser.ParserListener() {
            @Override
            public void startParser(XmlPullParser parser) {
                String name = parser.getName();
                if ("layout".equals(name)) {
                    final LayoutInfo info = new LayoutInfo();
                    XmlParser.runParser(parser, (String... ts) -> {
                        if ("name".equals(ts[0])) {
                            //记录引导名
                            info.name = ts[1];
                        } else if ("info".equals(ts[0])) {
                            //记录引导名
                            info.info = ts[1];
                        } else if ("sn".equals(ts[0])) {
                            //记录引导名
                            info.sn = ts[1];
                        } else if ("ignore".equals(ts[0])) {
                            //记录引导名
                            info.ignore = Boolean.valueOf(ts[1]);
                        }
                    });
                    configs.put(info.name, info);
                }
            }

            @Override
            public void endParser(XmlPullParser parser) {
            }
        };
    }
}
