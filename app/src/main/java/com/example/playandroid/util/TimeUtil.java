package com.example.playandroid.util;

import java.util.Calendar;

/**
 * 传入指定的hour,minute，获取与当前的时间差.
 */
public class TimeUtil {
    /**
     * 返回的时间差是以ms为单位.
     */
    public static Long getNextTime(int hour, int minute) {
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        //选择的定时时间
        long selectTime = calendar.getTimeInMillis();
        //当定时时间早于当前的时间时，延后一天.
        if(systemTime > selectTime){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            selectTime = calendar.getTimeInMillis();
        }
        //计算设定的时间到现在时间的时间差
        return selectTime - systemTime;
    }
}
