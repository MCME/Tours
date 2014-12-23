package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tours.tour.Tour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;
import org.bukkit.Bukkit;

/**
 * @author dags_ <dags@dags.me>
 */
public class ChatListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        

        if (tourPlayers.containsKey(p.getName()))
        {
            Tour t = tours.get(tourPlayers.get(p.getName()));
            
            if (t.getInChat().contains(p))
            {
                String[] chat = new String[2];
                chat[0] = e.getFormat().replace(e.getMessage(), "").replace("%1$s", p.getDisplayName()).replace("%2$s", "");
                chat[1] = e.getMessage();

                synchronized (this)
                {
                    t.tourChat(p, chat);
                }
                
                e.setCancelled(true);
            }
            else
            {
                for (String pName : tourPlayers.keySet())
                {
                    if (Bukkit.getOfflinePlayer(pName).isOnline())
                    {
                        if (tours.get(tourPlayers.get(pName)).getInChat().contains(Bukkit.getPlayer(pName)))
                        {
                            e.getRecipients().remove(Bukkit.getPlayer(pName));
                        }
                    }
                }
            }
        }
        else
        {
            for (String pName : tourPlayers.keySet())
            {
                if (Bukkit.getOfflinePlayer(pName).isOnline())
                {
                    if (tours.get(tourPlayers.get(pName)).getInChat().contains(Bukkit.getPlayer(pName)))
                    {
                        e.getRecipients().remove(Bukkit.getPlayer(pName));
                    }
                }
            }
        }
    }

}
