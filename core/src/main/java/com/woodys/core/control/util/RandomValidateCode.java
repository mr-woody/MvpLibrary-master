package com.woodys.core.control.util;

import java.util.Random;

/**
 * Created by ldfs on 16/3/30.
 */
public class RandomValidateCode {
    private static final char[] chars = {'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    /**
     * 获得一个随机的字符串 返回Color对象
     * @return
     */
    public static char getRandomCode() {
        Random ran = new Random();
        return chars[ran.nextInt(chars.length)];
    }

    /**
     * 获取干扰字符
     * @param source
     * @param c
     * @return
     */
    public static String getDisturbString(String source,char c) {
        int index=((int)c)%10%3;
        Random ran =null;
        String target=String.valueOf(c)+source;
        for (int i=0;i<index;i++){
            ran=new Random();
            target+=chars[ran.nextInt(chars.length)];
        }
        return target;
    }

}
