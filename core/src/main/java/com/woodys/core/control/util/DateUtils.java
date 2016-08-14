package com.woodys.core.control.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间工具类
 *
 * @author momo
 * @Date 2014/6/4
 */
public class DateUtils {
    public static final int MINUTES = 60 * 1000;// 分毫秒值
    public static final int HOUR = 60 * MINUTES;// 小时毫秒值
    public static final int DAY = 24 * HOUR;// 天毫秒值
    public static final int WEEK = 7 * DAY;// 周毫秒值

    /**
     * 格式化进度信息
     *
     * @param duration 时间数
     * @return
     */
    public static String getProgressTime(long duration) {
        return getFromat("mm:ss", duration);
    }

    public static String getFormatTime(long duration) {
        return getFromat("MM天dd日 HH:mm:ss", duration);
    }

    /**
     * 格式化时间值
     *
     * @param format   格化
     * @param duration 时间值
     * @return
     */
    public static String getFromat(String format, long duration) {
        return new SimpleDateFormat(format).format(getMillis(duration));
    }

    /**
     * 给定时间是否为今天时间值
     *
     * @param startThrntableTime
     * @return
     */
    public static boolean isToday(long startThrntableTime) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day, 0, 0, 0);
        return startThrntableTime > calendar.getTimeInMillis();
    }

    /**
     * 返回当前时间描述
     *
     * @param currentTime 当前时间
     * @return
     */
    public static String getTimeSummary(long currentTime) {
        long timeMillis = System.currentTimeMillis() - getMillis(currentTime);
        timeMillis = (0 > timeMillis) ? 0 : timeMillis;
        int scale = 60, i = 0;
        for (timeMillis /= 1000 * scale; timeMillis >= 0; timeMillis /= scale, i++) {
            switch (i) {
                case 0:
                    // 分
                    if (timeMillis < 1) {
                        return "刚刚";
                    } else if (timeMillis >= 1 && timeMillis < 60) {
                        return timeMillis + "分钟前";
                    }
                    break;
                case 1:
                    // 时
                    if (timeMillis < 24) {
                        return timeMillis + "小时前";
                    }
                    scale = 24;
                    break;
                case 2:
                    // 天
                    if (timeMillis < 28) {
                        return timeMillis < 7 ? timeMillis + "天前" : timeMillis / 7 == 0 ? "1周前" : timeMillis / 7 + "周前";
                    } else if (timeMillis >= 28 && timeMillis < 30) {
                        return "1月前";
                    }
                    scale = 30;
                    break;
                case 3:
                    if (timeMillis < 12) {
                        return timeMillis + "月前";
                    }
                    scale = 12;
                    break;
                case 4:
                    return timeMillis + "年前";
            }
        }
        return null;
    }

    /**
     * 根据给定时区字符串值,返回当前时间毫秒
     */
    public static long getTimeMillis(String createTime) {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return formater.parse(createTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 两个时间值是否为相同一天
     *
     * @param todayMillis
     * @param timeMillis
     * @return
     */
    public static boolean isSameDay(long todayMillis, long timeMillis) {
        if (-1 == todayMillis || -1 == timeMillis) return false;
        Calendar calcendar = Calendar.getInstance();
        calcendar.setTimeInMillis(todayMillis);
        int today = calcendar.get(Calendar.DAY_OF_MONTH);
        calcendar.setTimeInMillis(timeMillis);
        return today == calcendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天起始时间
     */
    public static long getToDayStartMillis() {
        Calendar calendar = Calendar.getInstance();
        return getMillis(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 今年起始时间
     */
    public static long getYearStartMillis() {
        return getMillis(0, 0);
    }

    private static long getMillis(int month, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(instance.get(Calendar.YEAR), month,
                day, 0, 0, 0);
        return instance.getTimeInMillis();
    }

    /**
     * 获取当前时间，可能服务器返回的是秒或者毫秒单位的时间戳
     * @param timeMillis
     * @return
     */
    public static long getMillis(long timeMillis){
        int size=String.valueOf(timeMillis).length();
        if (timeMillis>0 && size<= 10) {
            //说明是时间戳是秒为单位的
            timeMillis = timeMillis * 1000;
        }
        return timeMillis;
    }



    public static String getCurrentTime(long timeMillis) {
        int minute = (int) (getMillis(timeMillis) / 1000 / 60);
        int year = minute / 60 / 24 / 365;
        int month = minute / 60 / 24 / 30;
        int day = minute / 60 / 24;
        int hour = minute / 60;
        String timeValue = new String();
        if (0 != year) {
            timeValue += (year + "年");
        }
        if (0 != month) {
            timeValue += (month % 12 + "个月");
        }
        if (0 != day) {
            timeValue += (day % 30 + "天");
        }
        if (0 != hour) {
            timeValue += (hour % 24 + "小时");
        }
        if (0 != minute) {
            timeValue += (minute % 60 + "分钟");
        }
        return timeValue;
    }
}
