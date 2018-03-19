package com.liuh.signincalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Date: 2018/3/19 14:24
 * Description:
 */

public class DateUtil {

    public static int getTodayPosition(String dayData) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(dayData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_MONTH);//1表示第一天，以此后推
    }
}
