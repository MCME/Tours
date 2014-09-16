package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tours.Tours;
import com.mcmiddleearth.tours.api.NewPlayerEvent;
import com.mcmiddleearth.tours.tour.Tour;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;
import static com.mcmiddleearth.tours.utils.Colors.*;

/**
 * @author dags_ <dags@dags.me>
 */
public class PlayerListener implements Listener
{

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore())
        {
            Tours.getPlayerTracker().addNewPlayer(p);
            return;
        }
        if (Tours.getPlayerTracker().isTrackingPlayer(p))
        {
            return;
        }
        if (tours.size() > 0)
        {
            p.sendMessage(green + "There are 1 or more tours currently running!");
            p.sendMessage(yellow + "See " + green + "/tour help" + yellow + " for more info!");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        if (tourPlayers.containsKey(p.getName()))
        {
            if (tours.containsKey(p.getName()))
            {
                leaderQuit(p);
            }
            else
            {
                touristQuit(p);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerKicked(PlayerKickEvent e)
    {
        Player p = e.getPlayer();
        if (tourPlayers.containsKey(p.getName()))
        {
            if (tours.containsKey(p.getName()))
            {
                leaderQuit(p);
            }
            else
            {
                touristQuit(p);
            }
        }
    }

    private void leaderQuit(Player p)
    {
        Tour t = tours.get(p.getName());
        if (t == null)
        {
            return;
        }
        t.tourNotify(gray + "Your guide has left, the tour has ended!");
        for (String s : t.getTouristList())
        {
            if (tourPlayers.containsKey(s))
            {
                tourPlayers.remove(s);
            }
        }
        t.tourClear();
        if (p.getInventory().getHelmet() != null)
        {
            if (p.getInventory() instanceof Block)
                System.out.print("yes");
            p.getInventory().setHelmet(null);
        }
    }

    private void touristQuit(Player p)
    {
        Tour t = tours.get(tourPlayers.get(p.getName()));
        if (t == null)
        {
            return;
        }
        t.removeTourist(p);
        tourPlayers.remove(p.getName());
    }

}
