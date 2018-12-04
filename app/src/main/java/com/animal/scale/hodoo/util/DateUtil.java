package com.animal.scale.hodoo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }
    public static String getCurrentMonth() {
        return new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
    }

    public static long getCurrentMilicecond() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(getCurrentDatetime());
        return date.getTime();
    }

    public static long getMilicecond(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(time);
        return date.getTime();
    }
}
