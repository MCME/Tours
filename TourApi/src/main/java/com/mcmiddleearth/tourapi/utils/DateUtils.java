package com.mcmiddleearth.tourapi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class DateUtils
{

    private static DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    private static DateFormat time = new SimpleDateFormat("HH:mm:ss");

    public static String getDate(Date d)
    {
        return date.format(d);
    }

    public static String getTime(Date d)
    {
        return time.format(d);
    }

}
