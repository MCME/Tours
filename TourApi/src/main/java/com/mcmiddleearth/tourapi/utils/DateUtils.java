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

    /**
     * Format the given Date object and return as a String
     * @param d Date to format
     * @return String formatted date
     */
    public static String getDate(Date d)
    {
        return date.format(d);
    }

    /**
     * Format the given Date object and return as a String
     * @param d Date to format
     * @return String formatted time
     */
    public static String getTime(Date d)
    {
        return time.format(d);
    }

}
