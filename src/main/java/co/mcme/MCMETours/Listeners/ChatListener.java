package co.mcme.MCMETours.Listeners;

import co.mcme.MCMETours.MCMETours;
import co.mcme.MCMETours.Tour.Tour;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener
  implements Listener
{
  @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  public void PlayerChat(AsyncPlayerChatEvent e)
  {
    Player p = e.getPlayer();
    if (MCMETours.tourPlayers.containsKey(p.getName()))
    {
      String[] chat = new String[2];
      chat[0] = e.getFormat().replace(e.getMessage(), "").replace("%1$s", p.getDisplayName()).replace("%2$s", "");
      chat[1] = e.getMessage();
      
      Tour t = (Tour)MCMETours.tours.get(MCMETours.tourPlayers.get(p.getName()));
      e.getRecipients().removeAll(t.getTourists());
      
      t.tourChat(p, chat);
    }
  }
}
