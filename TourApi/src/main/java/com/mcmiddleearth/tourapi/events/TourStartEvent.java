package com.mcmiddleearth.tourapi.events;

import com.mcmiddleearth.tourapi.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class TourStartEvent extends Event implements TourEvent
{

    private static final HandlerList handlers = new HandlerList();
    private final Player leader;
    private final String tour;
    private final String date;
    private final String time;

    /**
     * The TourStartEvent fires when a new tour is created, providing basic information about the tour
     * to listeners (useful for logging tours maybe?)
     * @param p Player tour leader
     * @param s String name of tour
     */
    public TourStartEvent(Player p, String s)
    {
        Date d = new Date();
        leader = p;
        tour = s;
        date = DateUtils.getDate(d);
        time = DateUtils.getTime(d);
    }

    /**
     * Returns the Player that is leading the new tour
     * @return Player leader
     */
    public Player getLeader()
    {
        return leader;
    }

    /**
     * Returns the name of the newly created tour
     * @return String tour name
     */
    public String getTourName()
    {
        return tour;
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

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
