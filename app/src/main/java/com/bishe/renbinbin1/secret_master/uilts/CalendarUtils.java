package com.bishe.renbinbin1.secret_master.uilts;

import java.text.SimpleDateFormat;

public class CalendarUtils {
    /**
     * long型转换为String
     * @param l
     * @return
     */
    public static String forDateToString(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(l);
    }
}
