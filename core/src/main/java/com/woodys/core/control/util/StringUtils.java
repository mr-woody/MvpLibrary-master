package com.woodys.core.control.util;

import android.text.TextUtils;
import android.util.Log;

import com.woodys.core.control.logcat.Logcat;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ldfs on 15/6/2.
 */
public class StringUtils {
    private final static String TAG = StringUtils.class.getSimpleName();

    /**
     * 验证数据是否是英文
     *
     * @param str
     * @return
     */
    public static boolean validateEnglish(String str) {
        String regex = "^[a-zA-Z]+$";
        return matcher(regex, str);
    }

    /**
     * 验证数据是否是英文
     *
     * @param str
     * @return
     */
    public static boolean validatePassW(String str) {
        String regex = "[\\da-zA-Z]{6,20}";
        return matcher(regex, str);
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static int hasChineseLength(String strName) {
        int size = 0;
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                size++;
            }
        }
        return size;
    }

    /**
     * 对表情符，特殊字符进行过滤
     *
     * @param str
     * @return
     */
    public static String emojiFilter(String str) {
        // 只允许字母和数字和中文//[\\pP‘’“”
        String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
        Pattern p = Pattern.compile(regEx);
        StringBuilder sb = new StringBuilder(str);

        for (int len = str.length(), i = len - 1; i >= 0; --i) {

            if (!p.matches(regEx, String.valueOf(str.charAt(i)))) {
                sb.deleteCharAt(i);
            }
        }

        return sb.toString();
    }

    /**
     * 获取url地址中的主域名
     *
     * @param url
     * @return
     */
    public static String getServerUrl(String url) {
        String source = "";
        try {
            Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            if (matcher.find()) {
                source = matcher.group();
            }
        } catch (Exception e) {
            Logcat.e(TAG, e.getMessage());
        }
        return source;
    }


    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则‰返回 false;
     */
    public static boolean matcher(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 非空判断
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    //使用String的split 方法
    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    //List转换为字符串以，隔开
    public static String convertListToString(List<String> list) {

        Iterator<String> it = list.iterator();
        if (!it.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            String str = it.next();
            sb.append(str);
            if (!it.hasNext())
                return sb.toString();
            sb.append(',');
        }

    }

    /**
     * 判断两个字符是否相等
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isEquals(String source, String target) {
        return source != null ? source.equals(target) : false;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }


    /**
     * 获得字符串中数值
     *
     * @param value
     * @return
     */
    public static int convertStrToInt(String value) {
        int number = -1;
        if (!TextUtils.isEmpty(value)) {
            try {
                number = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                number = -1;
                Log.e("NumberFormatException", "FormatValue:" + value);
            }
        }
        return number;
    }

    /**
     * 从字符串中获得map键值
     *
     * @param result
     * @return
     */
    public static Map<String, String> jsonToMap(String result) {
        Map<String, String> params = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                params = new HashMap<>();
                JSONObject json = new JSONObject(result);
                Iterator<String> iKey = json.keys();
                while (iKey.hasNext()) {
                    String key = iKey.next();
                    Object value = json.opt(key);
                    if (JSONObject.NULL.equals(value)) {
                        value = null;
                    }
                    if (null != value && !TextUtils.isEmpty(value.toString())) {
                        params.put(key, value.toString());
                    }
                }
            } catch (Exception e) {
                Log.e("ResponseParamsError", "Exception:" + e.getMessage() + " Value:" + result);
            }
        }
        return params;
    }


}
