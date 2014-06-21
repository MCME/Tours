package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tourapi.TourApi;
import com.mcmiddleearth.tourapi.events.NewPlayerPassEvent;
import com.mcmiddleearth.tours.tour.Tour;
import com.mcmiddleearth.tours.tour.TourManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.mcmiddleearth.tours.utils.Colors.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class PlayerListener implements Listener
{

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onNewPlayer(NewPlayerPassEvent e)
    {
        String msg = lPurple + e.getPlayer().getName() + dPurple + " has just passed the New Player World!";
        Bukkit.getServer().broadcast(msg, "Tours.notify.newplayer");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if (TourApi.isTrackingPlayer(p))
        {
            return;
        }
        if (TourManager.getNumberOfTours() > 0)
        {
            p.sendMessage(green + "There are 1 or more tours currently running!");
            p.sendMessage(yellow + "See " + green + "/tour help" + yellow + " for more info!");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuit(PlayerQuitEvent e)
    {
        onQuit(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerKicked(PlayerKickEvent e)
    {
        onQuit(e.getPlayer());
    }

    private void onQuit(Player p)
    {
        if (TourManager.playerIsTouring(p))
        {
            if (TourManager.hasTour(p.getName()))
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
        Tour t = TourManager.getTour(p.getName());
        if (t == null)
        {
            return;
        }
        t.softCloseTour(gray + "Your guide has left, the tour has ended!");
        if (p.getInventory().getHelmet() != null)
        {
            if (p.getInventory() instanceof Block)
                System.out.print("yes");
            p.getInventory().setHelmet(null);
        }
        TourManager.removePlayer(p);
    }

    private void touristQuit(Player p)
    {
        Tour t = TourManager.getPlayerTour(p);
        if (t == null)
        {
            return;
        }
        t.removeTourist(p);
        TourManager.removePlayer(p);
    }

}
