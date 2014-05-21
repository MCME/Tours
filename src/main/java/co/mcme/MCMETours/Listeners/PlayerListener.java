package co.mcme.MCMETours.Listeners;

import co.mcme.MCMETours.MCMETours;
import co.mcme.MCMETours.Tour.Tour;
import co.mcme.MCMETours.Utils.Colors;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener
  implements Listener
{
  @EventHandler(priority=EventPriority.MONITOR)
  public void playerJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();
    if (!p.hasPlayedBefore()) {
      for (Player q : Bukkit.getOnlinePlayers()) {
        if (q.hasPermission("MCMETours.ranger")) {
          q.sendMessage(Colors.lPurple + p.getName() + Colors.dPurple + " is a newbie to the server, give 'em a hug!");
        }
      }
    }
    if (MCMETours.tours.size() == 0)
    {
      p.sendMessage(Colors.yellow + "There are no tours running right now.");
      p.sendMessage(Colors.yellow + "See " + Colors.green + "/tour help" + Colors.yellow + " for more info!");
    }
    if (MCMETours.tours.size() > 0)
    {
      p.sendMessage(Colors.green + "There are 1 or more tours currently running!");
      p.sendMessage(Colors.yellow + "See " + Colors.green + "/tour help" + Colors.yellow + " for more info!");
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void playerQuit(PlayerQuitEvent e)
  {
    Player p = e.getPlayer();
    if (MCMETours.tourPlayers.containsKey(p.getName())) {
      if (MCMETours.tours.containsKey(p.getName())) {
        leaderQuit(p);
      } else {
        touristQuit(p);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void playerKicked(PlayerKickEvent e)
  {
    Player p = e.getPlayer();
    if (MCMETours.tourPlayers.containsKey(p.getName())) {
      if (MCMETours.tours.containsKey(p.getName())) {
        leaderQuit(p);
      } else {
        touristQuit(p);
      }
    }
  }
  
  private void leaderQuit(Player p)
  {
    Tour t = (Tour)MCMETours.tours.get(p.getName());
    t.tourNotify(Colors.gray + "Your guide has left, the tour has ended!");
    for (String s : t.getTouristList()) {
      if (MCMETours.tourPlayers.containsKey(s)) {
        MCMETours.tourPlayers.remove(s);
      }
    }
    t.tourClear();
  }
  
  private void touristQuit(Player p)
  {
    Tour t = (Tour)MCMETours.tours.get(MCMETours.tourPlayers.get(p.getName()));
    t.removeTourist(p);
    
    MCMETours.tourPlayers.remove(p.getName());
  }
}
