package com.mcmiddleearth.tourapi.eventtriggers;

import com.mcmiddleearth.tourapi.TourApi;
import com.mcmiddleearth.tourapi.events.NewPlayerJoinEvent;
import com.mcmiddleearth.tourapi.events.NewPlayerPassEvent;
import com.mcmiddleearth.tourapi.events.TourEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author dags_ <dags@dags.me>
 */

public class NewPlayerEventTrigger implements Listener
{

    private final String world;
    private final String welcome;

    public NewPlayerEventTrigger(Plugin p)
    {
        Configuration c = p.getConfig();
        world = c.getString("NewPlayerSettings.NewPlayerWorld");
        welcome = ChatColor.translateAlternateColorCodes('&', c.getString("NewPlayerSettings.NewPlayerWelcome"));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore())
        {
            NewPlayerJoinEvent npj = new NewPlayerJoinEvent(p);
            TourApi.getPlayerTracker().addNewPlayer(p, npj.getDate(), npj.getTime());
            callEvent(npj);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent e)
    {
        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(world) && e.getFrom().getName().equalsIgnoreCase(world))
        {
            if (TourApi.isTrackingPlayer(e.getPlayer()))
            {
                TourApi.getPlayerTracker().removePlayer(e.getPlayer());
                callEvent(new NewPlayerPassEvent(e.getPlayer(), welcome));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNewPlayerPassMonitor(NewPlayerPassEvent e)
    {
        e.getPlayer().sendMessage(e.getWelcomeMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onNewPlayerJoin(NewPlayerJoinEvent e)
    {

    }

    private void callEvent(TourEvent te)
    {
        Bukkit.getPluginManager().callEvent(te.toEvent());
    }

}
