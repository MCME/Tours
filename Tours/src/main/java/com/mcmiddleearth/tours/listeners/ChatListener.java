package com.mcmiddleearth.tours.listeners;

import com.mcmiddleearth.tours.tour.Tour;
import com.mcmiddleearth.tours.tour.TourManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author dags_ <dags@dags.me>
 */

public class ChatListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void PlayerChat(AsyncPlayerChatEvent e)
    {
        final Player p = e.getPlayer();

        if (TourManager.playerIsTouring(p))
        {
            String[] chat = new String[2];
            chat[0] = e.getFormat().replace(e.getMessage(), "").replace("%1$s", p.getDisplayName()).replace("%2$s", "");
            chat[1] = e.getMessage();

            Tour t = TourManager.getPlayerTour(p);

            if (t != null)
            {
                e.getRecipients().removeAll(t.getTourists());
                synchronized (this)
                {
                    t.tourChat(p, chat);
                }
            }
        }
    }

}
