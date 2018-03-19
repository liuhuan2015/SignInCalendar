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

    //获取某一天是星期中的第几天
    public static int getWeek(String weekData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(weekData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);//1表示星期日，以此后推
    }

    //获取某个月的天数
    public static int getMonthDayNum(String monthData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(monthData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.MONTH, 1);//设为下一个月
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
