package com.mcmiddleearth.tourapi.events;

import com.mcmiddleearth.tourapi.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

/**
 * @author dags_ <dags@dags.me>
 */

public class NewPlayerPassEvent extends Event implements TourEvent
{

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private String welcomeMessage;
    private final String date;
    private final String time;

    /**
     * The NewPlayerEvent fires after a 'tracked' player changes from the new player world to a different one.
     * Tours tracks players that are brand-new to the server until they leave the new player world.
     */
    public NewPlayerPassEvent(Player p, String s)
    {
        Date d = new Date();
        player = p;
        date = DateUtils.getDate(d);
        time = DateUtils.getTime(d);
        welcomeMessage = s;
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
     * Returns the welcome message that the new player will see
     * @return String
     */
    public String getWelcomeMessage()
    {
        return welcomeMessage.replace("%DATE%", date).replace("%TIME%", time);
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

    /**
     * Set the welcome message to be sent to the new player
     * @param newMessage String message
     */
    public void setWelcomeMessage(String newMessage)
    {
        welcomeMessage = newMessage;
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
