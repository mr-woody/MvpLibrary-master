package com.woodys.core.control.preference.reader;


import com.woodys.core.control.preference.config.LayoutInfo;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

/**
 * Created by cz on 15/12/14.
 */
@Config(value = "config/sn.xml")
public class LayoutSnReader extends AssetReader<String, String> {
    @Override
    public XmlParser.ParserListener getParserListener(HashMap<String, String> configs) {
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
                        } else if ("sn".equals(ts[0])) {
                            //记录引导名
                            info.sn = ts[1];
                        }
                    });
                    configs.put(info.name, info.sn);
                }
            }

            @Override
            public void endParser(XmlPullParser parser) {
            }
        };
    }
}
