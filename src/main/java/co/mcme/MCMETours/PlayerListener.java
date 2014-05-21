package co.mcme.MCMETours;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
  public void PlayerJoin(PlayerJoinEvent event)
  {
    Player p = event.getPlayer();
    if (!p.hasPlayedBefore()) {
      for (Player g : Bukkit.getOnlinePlayers()) {
        if (g.hasPermission("MCMETours.ranger")) {
          g.sendMessage(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.DARK_PURPLE + " is a newbie to the server, give 'em a hug!");
        }
      }
    }
    if (CommandMethods.group.size() == 0)
    {
      p.sendMessage(ChatColor.YELLOW + "There are no tours running right now.");
      
      p.sendMessage(ChatColor.YELLOW + "See " + ChatColor.GREEN + "/tour help" + ChatColor.YELLOW + " for more info!");
    }
    if (CommandMethods.group.size() > 0)
    {
      p.sendMessage(ChatColor.GREEN + "There are 1 or more tours currently running!");
      
      p.sendMessage(ChatColor.YELLOW + "See " + ChatColor.GREEN + "/tour help" + ChatColor.YELLOW + " for more info!");
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void PlayerQuit(PlayerQuitEvent event)
  {
    Player p = event.getPlayer();
    if (p.hasPermission("MCMETours.Ranger")) {
      RangerQuit(p);
    } else if (CommandMethods.group.containsKey(p)) {
      CommandMethods.group.remove(p);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void PlayerKicked(PlayerKickEvent event)
  {
    Player p = event.getPlayer();
    if ((p.hasPermission("MCMETours.Ranger")) && (CommandMethods.group.containsValue(p.getName().toLowerCase()))) {
      RangerQuit(p);
    } else if (CommandMethods.group.containsKey(p)) {
      CommandMethods.group.remove(p);
    }
  }
  
  private void RangerQuit(Player p)
  {
    CommandMethods.group.remove(p);
    for (Player q : Bukkit.getOnlinePlayers()) {
      if ((CommandMethods.group.containsKey(q)) && (((String)CommandMethods.group.get(q)).contains(p.getName())) && (p != q))
      {
        CommandMethods.group.remove(q);
        q.sendMessage(ChatColor.RED + "Your guide has left. The tour is now over!");
      }
    }
  }
}
