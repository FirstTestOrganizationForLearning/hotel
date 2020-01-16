/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fntx.admin.utils;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 日期转化成 (2016-09-09)
     *
     * @param strDate
     * @return
     */
    public static String YearAndMonthToDate2(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
        SimpleDateFormat SDF = new SimpleDateFormat("MM-dd");
        String date = null;
        try {
            date = SDF.format(simpleDateFormat.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较 ， 如果s > = e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期转换为制定的字符串格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToStr(Date date, String format) {
        if (null == date) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期转换为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToStrOfDefaulfFormat(Date date) {
        return dateToStr(date, FORMAT_DEFAULT);
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long aa = 0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * <li>功能描述：时间相减得到小时数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author kang
     */
    public static long getHourSub(Date beginDateStr, Date endDateStr) {
        long hour = (endDateStr.getTime() - beginDateStr.getTime()) / (60 * 60 * 1000);
        return hour;
    }

    /**
     * <li>功能描述：时间相减得到分钟数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author kang
     */
    public static long getMinuteSub(Date beginDateStr, Date endDateStr) {
        long minute = (endDateStr.getTime() - beginDateStr.getTime()) / (60 * 1000);
        return minute;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static void main(String[] args) {
        System.out.println(getDays());
        System.out.println(getAfterDayWeek("3"));
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(String date) {
        if ("" != date) {
            String[] strs = date.split(" ");
            for (int i = 0; i < strs.length; i++) {
                date = strs[0];
            }
        }
        return date;
    }

    /**
     * 获取HH:mm格式
     *
     * @return
     */
    public static String getTime(String date) {
        if ("" != date) {
            String[] strs = date.split(" ");
            for (int i = 0; i < strs.length; i++) {
                date = strs[1];
            }
        }
        return date;
    }

    /**
     * 字符串转化成时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转化成 (20160909)
     *
     * @param strDate
     * @return
     */
    public static String YearAndMonthToDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        String date = null;
        try {
            date = SDF.format(simpleDateFormat.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 校验日期格式是否正确
     *
     * @param date
     * @return
     */
    public boolean checkDate(String date) {

        boolean flag = false;

        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(date);
        flag = m.matches();

        return flag;
    }

    /**
     * 把long 转换成 日期 再转换成String类型
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * <li>功能描述：增加天数
     *
     * @param s 当前时间
     * @param n 增加的天数
     * @return long
     * @author Administrator
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }

    }

    // date2比date1多的天数
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    public static Date strToDateDefault(String dateStr) {
        return strToDate(dateStr, FORMAT_DEFAULT);
    }

    /**
     * 日期转化成 (15Feb(Wed))
     *
     * @param strDate
     * @return
     */
    public static String DateToWeekAndMonth(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMM(E)", Locale.ENGLISH);
        return dateFormat.format(strToDate(strDate, "yyyy-MM-dd"));
    }

    /**
     * 日期转化成 (15Feb17)
     *
     * @param strDate
     * @return
     */
    public static String DateToYearAndMonth(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
        return dateFormat.format(strToDate(strDate, "yyyy-MM-dd"));
    }

    /**
     * 日期转化成 (15Feb)
     *
     * @param strDate
     * @return
     */
    public static String DateToDayAndMonth(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
        return dateFormat.format(strToDate(strDate, "yyyy-MM-dd"));
    }

    /**
     * 日期转化成 (15Feb(Wed))
     *
     * @param strDate
     * @return
     */
    public static String DateToDayAndMonth2(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
        return dateFormat.format(strToDate(strDate, "yyyyMMdd"));
    }


    public static String strToDateOfDefaulfFormat(String dateStr) {
        if (dateStr == null || dateStr.equals("null")) {
            return null;
        }
        return dateToStr(strToDate(dateStr, FORMAT_DEFAULT), FORMAT_DEFAULT);

    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    /**
     * 日期转成时间戳
     *
     * @param date
     * @return
     */
    public static long dateToStp(Date date) {
        long times = date.getTime();
        return times;
        //输出结果：1386665346113
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }


    //把月份转成英文
    private String getMonth(String month) {
        String Return;
        switch (month) {
            case "01":
                Return = "Jan";
                break;
            case "02":
                Return = "Feb";
                break;
            case "03":
                Return = "Mar";
                break;
            case "04":
                Return = "Apr";
                break;
            case "05":
                Return = "May";
                break;
            case "06":
                Return = "Jun";
                break;
            case "07":
                Return = "Jul";
                break;
            case "08":
                Return = "Aug";
                break;
            case "09":
                Return = "Sep";
                break;
            case "10":
                Return = "Oct";
                break;
            case "11":
                Return = "Nov";
                break;
            case "12":
                Return = "Dec";
                break;
            default:
                Return = "error";
        }
        return Return;
    }

}
