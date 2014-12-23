package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tours.tour.Tour;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;

/**
 * @author dags_ <dags@dags.me>
 */
public class ChatListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void PlayerChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();

        if (tourPlayers.containsKey(p.getName()))
        {
            if (tours.get(tourPlayers.get(p.getName())).getInChat().contains(p))
            {
                String[] chat = new String[2];
                chat[0] = e.getFormat().replace(e.getMessage(), "").replace("%1$s", p.getDisplayName()).replace("%2$s", "");
                chat[1] = e.getMessage();

                Tour t = tours.get(tourPlayers.get(p.getName()));
                e.getRecipients().removeAll(t.getTourists());

                synchronized (this)
                {
                    t.tourChat(p, chat);
                }
            }
            else
            {
                
            }
        }
    }

}
