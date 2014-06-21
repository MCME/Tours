package com.mcmiddleearth.tourapi.events;

import com.mcmiddleearth.tourapi.utils.DateUtils;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class TourStopEvent extends Event implements TourEvent
{

    private static final HandlerList handlers = new HandlerList();
    private final String tour;
    private final int size;
    private final String date;
    private final String time;

    /**
     * The TourStopEvent fires when a tour is closed, providing basic information about the tour
     * to listeners (useful for logging tours maybe?)
     * @param s String name of tour
     * @param i int size of tour
     */
    public TourStopEvent(String s, int i)
    {
        Date d = new Date();
        tour = s;
        size = i;
        date = DateUtils.getDate(d);
        time = DateUtils.getTime(d);
    }

    /**
     * Returns the name of the stopped tour
     * @return String tour name
     */
    public String getTourName()
    {
        return tour;
    }

    /**
     * Returns the size of the tour on closing
     * @return int size of tour
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Returns the date of this event
     * @return String value of today's date (day/month/year format)
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Returns the time of this event
     * @return String value of the current time (hour:minute:seconds format)
     */
    public String getTime()
    {
        return time;
    }

    @Override
    public Event toEvent()
    {
        return this;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

}
