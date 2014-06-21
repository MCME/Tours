package com.mcmiddleearth.tourapi.events;

import com.mcmiddleearth.tourapi.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class TourJoinEvent extends Event implements TourEvent
{

    private static final HandlerList handlers = new HandlerList();
    private final Player joined;
    private final String tour;
    private final int size;
    private final String date;
    private final String time;

    /**
     * The TourJoinEvent fires when a player joins a tour, providing basic information about the tour
     * to listeners (useful for logging tours maybe?)
     * @param p Player that has joined the tour
     * @param s String name of tour
     * @param i int size of tour (after player has joined)
     */
    public TourJoinEvent(Player p, String s, int i)
    {
        Date d = new Date();
        joined = p;
        tour = s;
        size = i;
        date = DateUtils.getDate(d);
        time = DateUtils.getTime(d);
    }

    /**
     * Returns the Player that has joined the tour
     * @return Player leader
     */
    public Player getJoined()
    {
        return joined;
    }

    /**
     * Returns the name of the joined tour
     * @return String tour name
     */
    public String getTourName()
    {
        return tour;
    }

    /**
     * Returns the size of the tour after the player has joined
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

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
