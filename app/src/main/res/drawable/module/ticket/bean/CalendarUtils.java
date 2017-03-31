package com.weiruanit.lifepro.module.ticket.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class CalendarUtils {

    public static long oneDay = 86400000;//一天的毫秒值
    private static String[] weeks = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     *
     * 字符串的日期转换为long
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static long formatTime(String s) throws ParseException {
        if (s != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(s).getTime();
        }
        return 0;
    }

    /**
     * long型转换为String
     * @param l
     * @return
     */
    public static String forDateToString(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }

    public static String initCalendar(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = (c.get(Calendar.MONTH)) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    /**
     * 获取星期
     * @param date
     * @return
     */
    public static String getWeek(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(simpleDateFormat.parse(date));

            int dayNum = calendar1.get(Calendar.DAY_OF_WEEK);

            if (dayNum == 1) {
                return weeks[0];
            } else if (dayNum == 2) {
                return weeks[1];
            } else if (dayNum == 3) {
                return weeks[2];
            } else if (dayNum == 4) {
                return weeks[3];
            } else if (dayNum == 5) {
                return weeks[4];

            } else if (dayNum == 6) {
                return weeks[5];
            } else if (dayNum == 7) {
                return weeks[6];
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
