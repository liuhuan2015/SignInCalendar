package com.liuh.signincalendar;

/**
 * 用户签到的数据对象
 */
public class UserBookInData {
    //签到时间，服务器返回格式未知，暂定为"xxxx-xx-xx"
    private String signtime;
    //是否已签到，默认为false
    private boolean isBookined = false;
    //是否是当天，默认为false
    private boolean isToday = false;
    //是否是其他月份的同天，默认为false
    private boolean isTodayInOtherMonth = false;

    public boolean isTodayInOtherMonth() {
        return isTodayInOtherMonth;
    }

    public void setIsTodayInOtherMonth(boolean isTodayInOtherMonth) {
        this.isTodayInOtherMonth = isTodayInOtherMonth;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public boolean isBookined() {
        return isBookined;
    }

    public void setIsBookined(boolean isBookined) {
        this.isBookined = isBookined;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setIsToday(boolean isToday) {
        this.isToday = isToday;
    }
}
