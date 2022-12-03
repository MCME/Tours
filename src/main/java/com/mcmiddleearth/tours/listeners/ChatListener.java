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

import java.util.Arrays;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */
public class ChatListener implements Listener
{
    private static final List<String> strings = Arrays.asList("$","s","%","$","1","2");

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerChat(AsyncPlayerChatEvent e)
    {
        String replacement = "";
        Player p = e.getPlayer();


        if (tourPlayers.containsKey(p.getName()))
        {
            Tour t = tours.get(tourPlayers.get(p.getName()));
            
            if (t.getInChat().contains(p))
            {
                if(strings.contains(e.getMessage())) replacement = e.getMessage();
                String[] chat = new String[2];
                chat[0] = e.getFormat().replace(e.getMessage(), replacement).replace("%1$s", p.getDisplayName()).replace("%2$s", "");
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
