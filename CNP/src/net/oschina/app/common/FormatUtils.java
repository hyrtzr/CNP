package net.oschina.app.common;

import java.text.*;
import java.util.Date;

public class FormatUtils
{
	
	  private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FormatUtils()
    {
    }

    public static String formatDate(Date date)
    {
        return dateFormat.format(date);
    }

}
