package com.fntx.search.utils;

import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Copyright (C), 2019, 琥珀丶时光
 * @ClassName: TimeUtil
 * @Author: 王俊文
 * @Date: 2019/8/29 上午9:53
 * @Description: 时间处理工具.  用于处理高并发下有概率出现异常的情况.
 */
@Component
public class TimeUtil
{
    public static final String YEAR_MONTH_DAY_SECOND_MILLI_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00";
    public static final String YEAR_MONTH_DAY_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_SECOND2 = "yyyy/MM/dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_SECOND3 = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY2 = "yyyy年MM月dd日";
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String DAY = "dd";

    /**
     * 采用 ThreadLocal 避免 SimpleDateFormat 非线程安全的问题
     * Key —— 时间格式
     * Value —— 解析特定时间格式的 SimpleDateFormat
     */
    private static ThreadLocal<Map<String, SimpleDateFormat>> sThreadLocal = new ThreadLocal<>();

    /**
     * 获取解析特定时间格式的 SimpleDateFormat
     *
     * @param pattern 时间格式
     */
    private static SimpleDateFormat getDateFormat(String pattern)
    {
        Map<String, SimpleDateFormat> strDateFormatMap = sThreadLocal.get();

        if (strDateFormatMap == null) {
            strDateFormatMap = new HashMap<>();
        }

        SimpleDateFormat simpleDateFormat = strDateFormatMap.get(pattern);
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            strDateFormatMap.put(pattern, simpleDateFormat);
            sThreadLocal.set(strDateFormatMap);
        }

        return simpleDateFormat;
    }

    /**
     * 时间格式化
     *
     * @param date：要格式化的时间
     * @param pattern：要格式化的类型
     */
    public static String formatDate(Date date, String pattern)
    {
        if (date == null || pattern == null)
        {
            return null;
        }
        return getDateFormat(pattern).format(date);
    }

    /**
     * 字符串转时间
     *
     * @param date：要格式化的时间
     * @param pattern：要格式化的类型
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        if (date == null || pattern == null)
        {
            return null;
        }
        return getDateFormat(pattern).parse(date);
    }
}
