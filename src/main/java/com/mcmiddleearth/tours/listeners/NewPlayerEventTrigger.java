package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tours.Tours;
import com.mcmiddleearth.tours.api.NewPlayerEvent;
import com.mcmiddleearth.tours.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * @author dags_ <dags@dags.me>
 */

public class NewPlayerEventTrigger implements Listener
{

    private final String world;
    private final String welcome;

    public NewPlayerEventTrigger()
    {
        Configuration c = Tours.inst().getConfig();
        world = c.getString("NewPlayerSettings.NewPlayerWorld");
        welcome = c.getString("NewPlayerSettings.NewPlayerWelcome"); //no function found, no javadoc
    }

    /*@EventHandler(priority = EventPriority.MONITOR) removed function, not needed for MCME
    public void onWorldChange(PlayerChangedWorldEvent e)
    {
        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(world) && e.getFrom().getName().equalsIgnoreCase(world))
        {
            if (Tours.getPlayerTracker().isTrackingPlayer(e.getPlayer()))
            {
                Bukkit.getPluginManager().callEvent(new NewPlayerEvent(e.getPlayer(), welcome));
            }
        }
    }
    */
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onNewPlayerMonitor(NewPlayerEvent e)
    {
        e.getPlayer().sendMessage(e.getWelcomeMessage());
    }

}
