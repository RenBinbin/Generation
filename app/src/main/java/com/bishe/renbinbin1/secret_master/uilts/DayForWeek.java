package com.bishe.renbinbin1.secret_master.uilts;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class DayForWeek {

    public static String dayForWeek(String pTime) throws Exception {
        String[] weeks = {"周一","周二","周三","周四","周五","周六","周日"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        int day = c.get(Calendar.DAY_OF_WEEK);
        if( day == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return weeks[dayForWeek - 1];
    }
}
