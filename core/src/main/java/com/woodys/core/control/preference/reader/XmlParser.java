package com.woodys.core.control.preference.reader;

import android.util.Xml;

import com.woodys.core.control.util.IOUtils;
import com.woodys.core.listener.ITask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析xml内容体
 * 
 * @author momo
 * @Date 2014/11/25
 * 
 */
public class XmlParser {
	/**
	 * 开始解析xml
	 * 
	 * @param inputStream
	 * @param listener
	 */
	public static void startParser(InputStream inputStream, ParserListener listener) {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "utf-8");
			int eventType = parser.getEventType();
			while (XmlPullParser.END_DOCUMENT != eventType) {
				if (null != listener) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						listener.startParser(parser);
						break;
					case XmlPullParser.END_TAG:
						listener.endParser(parser);
						break;
					default:
						break;
					}
				}
				eventType = parser.next();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inputStream);
		}
	}

	public static void runParser(XmlPullParser parser, ITask<String> task) {
		for (int i = 0; i < parser.getAttributeCount(); i++) {
			if (null != task) {
				task.run(parser.getAttributeName(i), parser.getAttributeValue(i));
			}
		}
	}

	public interface ParserListener {
		void startParser(XmlPullParser parser);

		void endParser(XmlPullParser parser);
	}

	public interface ParserResultListener<E> {
		void parserResult(E e);
	}
}
