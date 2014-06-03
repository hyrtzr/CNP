package com.jingdong.common.utils;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

public class FormatUtils {

	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public FormatUtils() {
	}

    /**
     * 返回当天的时间
     * @param date
     * @return
     */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

    /**
     * 返回明天的时间
     * @param date
     * @return
     */
    public static String nextDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,24);
        date.setTime(calendar.getTime().getTime());
        return formatDate(date);
    }

    /**
     * 返回后台天的时间
     * @param date
     * @return
     */
    public static String preDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,-24);
        date.setTime(calendar.getTime().getTime());
        return formatDate(date);
    }

}
