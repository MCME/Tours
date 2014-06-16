package com.mcmiddleearth.tourapi.events;

import com.mcmiddleearth.tourapi.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class NewPlayerJoinEvent extends Event implements TourEvent
{

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String date;
    private final String time;

    /**
     * The NewPlayerJoinEvent fires after a player connects to the server for the very first time. His UUID, join-date and
     * join-time will already have been logged by the PlayerTracker instance.
     */
    public NewPlayerJoinEvent(Player p)
    {
        Date d = new Date();
        player = p;
        date = DateUtils.getDate(d);
        time = DateUtils.getTime(d);
    }

    /**
     * Returns the Player who has just left the new player world
     * @return Player
     */
    public Player getPlayer()
    {
        return player;
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
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

    @Override
    public Event toEvent()
    {
        return this;
    }
}
