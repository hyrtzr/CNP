package com.hyrt.cnp.base.account.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.hyrt.cnp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/** 
 * 字符串操作工具包
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils 
{
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	//private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	/**
	 * 将字符串转位日期类型
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else if(days > 10){			
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	/**
	 * 判断给定字符串时间是否为今日
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate){
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if(time != null){
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 判断给定字符串是否空白串。
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	public static double toDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return toDouble(str,0.0);
		}
	}
	
	public static double toDouble(String str,double defValue){
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defValue;
		}
	}
    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 根据日期获得星期
     * @param pTime
     * @return
     */
    public static String getWeekOfDate(String pTime) throws ParseException {
        String[] weekDaysName = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ,"星期日"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return weekDaysName[dayForWeek-1];
    }

    /**
     * 将毫秒数转换为时间
     * yyyy-MM-dd HH:mm:ss
     * */

    public static String millTimeToNormalTime(String stime)throws Exception{
        if(stime.length() <= 0){
            return "";
        }
        Long timestamp = Long.parseLong(stime)*1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 将毫秒数转换为时间
     * yyyy-MM-dd
     * */

    public static String millTimeToNormalTime2(String stime)throws Exception{
        Long timestamp = Long.parseLong(stime)*1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 将毫秒数转换为时间
     * yyyy-MM-dd HH:mm:ss
     * */

    public static String millTimeToNormalTime3(String stime)throws Exception{
        Long timestamp = Long.parseLong(stime)*1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }

    /**
     * 动态数据类型
     * */

    public String getDynamicType(int id){
        switch (id){
//            case
        }
        return null;
    }

    private static Map<String, Integer> brows = new HashMap<String, Integer>();

    private static void loadBrow(){
        brows.put("[img]哈哈[/img]", R.drawable.laugh);

        brows.put("[img]鄙视[/img]", R.drawable.bs2_thumb);

        brows.put("[img]闭嘴[/img]", R.drawable.bz_thumb);

        brows.put("[img]偷笑[/img]", R.drawable.heia_thumb);

        brows.put("[img]吃惊[/img]", R.drawable.cj_thumb);

        brows.put("[img]可怜[/img]", R.drawable.kl_thumb);

        brows.put("[img]懒得理你[/img]", R.drawable.ldln_thumb);

        brows.put("[img]太开心[/img]", R.drawable.mb_thumb);

        brows.put("[img]爱你[/img]", R.drawable.lovea_thumb);

        brows.put("[img]亲亲[/img]", R.drawable.qq_thumb);

        brows.put("[img]泪[/img]", R.drawable.sada_thumb);

        brows.put("[img]生病[/img]", R.drawable.sb_thumb);

        brows.put("[img]害羞[/img]", R.drawable.shamea_thumb);

        brows.put("[img]呵呵[/img]", R.drawable.smilea_thumb);

        brows.put("[img]嘻嘻[/img]", R.drawable.tootha_thumb);

        brows.put("[img]可爱[/img]", R.drawable.tza_thumb);

        brows.put("[img]挤眼[/img]", R.drawable.zy_thumb);

        brows.put("[img]阴险[/img]", R.drawable.yx_thumb);

        brows.put("[img]右哼哼[/img]", R.drawable.yhh_thumb);

        brows.put("[img]来[/img]", R.drawable.come_thumb);
    }

    public static SpannableString getSpannableString(String string, final Context context){
        SpannableString spannable = new SpannableString(string+" ");
        loadBrow();
        String tempStr = string;

        while ((tempStr.indexOf("[img]") >= 0 && tempStr.indexOf("[/img]") >= 0)
                || (tempStr.indexOf("@") >= 0 && tempStr.indexOf("//") >= 0)) {
            if(tempStr.indexOf("@") < 0 || tempStr.indexOf("//") < 0){
                tempStr = tempStr.replace("@", "");
                tempStr = tempStr.replace("//", "");
            }
            if(tempStr.indexOf("[img]") < 0 || tempStr.indexOf("[/img]") < 0){
                tempStr = tempStr.replace("[img]", "");
                tempStr = tempStr.replace("[/img]", "");
            }
            int aboutStart = tempStr.lastIndexOf("@");
            int aboutEnd = tempStr.lastIndexOf("//");
            int browStart = tempStr.lastIndexOf("[img]");
            int browEnd = tempStr.lastIndexOf("[/img]");


//            android.util.Log.i("tag", "aboutStart:" + aboutStart + " aboutEnd:" + aboutEnd + " browStart:" + browStart + " browEnd:" + browEnd);

            if (aboutStart >= browEnd) {
                if (aboutStart < aboutEnd){
                    String str = tempStr.substring(aboutStart, aboutEnd);
//                android.util.Log.i("tag", "str:" + str);
                    tempStr = tempStr.substring(0, aboutStart);

                    spannable.setSpan(new ForegroundColorSpan(
                                    context.getResources().getColor(R.color.sendbtn_enable_color)),
                            aboutStart, aboutEnd,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }else{
                    tempStr = tempStr.substring(0, tempStr.length()-1);
                }

            } else if (browStart >= aboutEnd) {
                if(browStart < browEnd){
                    final String str = tempStr.substring(browStart, browEnd+6);
//                android.util.Log.i("tag", "str:" + str);
                    tempStr = tempStr.substring(0, browStart);

                    Drawable drawable = context.getResources().getDrawable((brows.get(str)));
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    spannable.setSpan(
                            span,
                            browStart,
                            browEnd+6,
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }else{
                    tempStr = tempStr.substring(0, tempStr.length()-1);
                }

            }else{
                tempStr = tempStr.substring(0, tempStr.length()-1);
            }
        }

        return spannable;
    }

  }
