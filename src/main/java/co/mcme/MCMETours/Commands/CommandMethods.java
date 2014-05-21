package co.mcme.MCMETours.Commands;

import co.mcme.MCMETours.MCMETours;
import co.mcme.MCMETours.Tour.Tour;
import co.mcme.MCMETours.Utils.Colors;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandMethods
{
  public static void tourInfo(Player p)
  {
    if (MCMETours.tours.isEmpty())
    {
      p.sendMessage(Colors.yellow + "There are no tours running right now.");
      p.sendMessage(Colors.green + "Online Rangers: ");
      for (Player q : Bukkit.getOnlinePlayers()) {
        if ((q.hasPermission("MCMETours.ranger")) && (!q.hasPermission("MCMETours.admin"))) {
          p.sendMessage(Colors.aqua + q.getName());
        }
      }
    }
    else
    {
      p.sendMessage(Colors.green + "Currently running tours:");
      for (String s : MCMETours.tours.keySet()) {
        p.sendMessage(Colors.aqua + s);
      }
    }
  }
  
  public static void tourRequest(Player p)
  {
    p.sendMessage(Colors.yellow + "Request sent!");
    for (Player q : Bukkit.getOnlinePlayers()) {
      if ((q.hasPermission("MCMETours.ranger")) && (q.hasPermission("MCMETours.admin"))) {
        q.sendMessage(Colors.green + p.getName() + Colors.yellow + " requests a tour!");
      }
    }
  }
  
  public static void tourJoin(Player p, String s)
  {
    if (!MCMETours.tours.keySet().contains(p.getName()))
    {
      if (!MCMETours.tourPlayers.containsKey(p.getName()))
      {
        String tourName = "null";
        for (String st : MCMETours.tours.keySet()) {
          if (st.toLowerCase().contains(s.toLowerCase()))
          {
            tourName = st;
            break;
          }
        }
        if (!tourName.equals("null"))
        {
          Tour t = (Tour)MCMETours.tours.get(tourName);
          
          t.addTourist(p);
          MCMETours.tourPlayers.put(p.getName(), t.getTourName());
          
          OfflinePlayer op = Bukkit.getOfflinePlayer(t.getTourName());
          if (op.isOnline()) {
            p.teleport(((Player)op).getLocation());
          }
        }
      }
      else
      {
        p.sendMessage(Colors.gray + "You are already on a tour! You must leave it first.");
      }
    }
    else {
      p.sendMessage(Colors.gray + "You are currently running a tour! You must stop it first.");
    }
  }
  
  public static void tourLeave(Player p)
  {
    if (!MCMETours.tours.keySet().contains(p.getName()))
    {
      if (MCMETours.tourPlayers.containsKey(p.getName()))
      {
        Tour t = (Tour)MCMETours.tours.get(MCMETours.tourPlayers.get(p.getName()));
        t.removeTourist(p);
        
        MCMETours.tourPlayers.remove(p.getName());
      }
      else
      {
        p.sendMessage(Colors.gray + "You are not currently on a tour!");
      }
    }
    else {
      p.sendMessage(Colors.gray + "You are currently running a tour! You must stop it first.");
    }
  }
  
  public static void tourHelp(Player p)
  {
    p.sendMessage(Colors.gray + "/tour - check for tour information");
    p.sendMessage(Colors.gray + "/tour request - submit a request for a tour");
    p.sendMessage(Colors.gray + "/tour join <RangerName> - join Ranger's tour");
    p.sendMessage(Colors.gray + "/tour leave - leave current tour");
    if (p.hasPermission("MCMETours.ranger"))
    {
      p.sendMessage(Colors.gray + "/tour start - host a tour");
      p.sendMessage(Colors.gray + "/tour stop - stop your tour");
      p.sendMessage(Colors.gray + "/tour hat - wear a Glowstone hat");
      p.sendMessage(Colors.gray + "/tour list - list users on your tour");
      p.sendMessage(Colors.gray + "/tourtp <playername> - tp player on your tour to you");
      p.sendMessage(Colors.gray + "/tourtpa - tp all players on your tour to you");
    }
  }
  
  public static void tourStart(Player p)
  {
    if (!MCMETours.tours.containsKey(p.getName()))
    {
      if (MCMETours.tourPlayers.containsKey(p.getName()))
      {
        Tour t = (Tour)MCMETours.tours.get(MCMETours.tourPlayers.get(p.getName()));
        t.removeTourist(p);
      }
      Tour t = new Tour(p);
      MCMETours.tours.put(t.getTourName(), t);
      t.tourNotify(Colors.green + "Tour started!");
      
      MCMETours.tourPlayers.put(p.getName(), t.getTourName());
      
      Bukkit.broadcastMessage(Colors.green + "Tour starting soon with " + Colors.aqua + p.getName());
      Bukkit.broadcastMessage(Colors.green + "Enter " + Colors.yellow + "/tour join " + p.getName() + Colors.green + " to join in!");
    }
    else
    {
      p.sendMessage(Colors.gray + "You are already leading a tour!");
    }
  }
  
  public static void tourStop(Player p)
  {
    if (MCMETours.tours.containsKey(p.getName()))
    {
      Tour t = (Tour)MCMETours.tours.get(p.getName());
      
      t.tourNotify(Colors.yellow + "Tour has ended!");
      t.tourClear();
      
      MCMETours.tourPlayers.remove(p.getName());
    }
  }
  
  public static void tourHat(Player p)
  {
    if (MCMETours.tours.containsKey(p.getName()))
    {
      if (!p.getItemInHand().getType().equals(Material.AIR))
      {
        p.getInventory().setHelmet(new ItemStack(p.getItemInHand().getType()));
        p.sendMessage(Colors.green + "Hat added!");
      }
      else
      {
        p.getInventory().setHelmet(null);
        p.sendMessage(Colors.gray + "Hat removed!");
      }
    }
    else {
      p.sendMessage(Colors.gray + "You need to be leading a tour to use that!");
    }
  }
  
  public static void tourList(Player p)
  {
    if (MCMETours.tours.containsKey(p.getName()))
    {
      Tour t = (Tour)MCMETours.tours.get(p.getName());
      
      p.sendMessage(Colors.yellow + t.getTouristList().toString());
    }
  }
  
  public static void tourTp(Player p, String s)
  {
    if (MCMETours.tourPlayers.containsKey(p.getName()))
    {
      Tour t = (Tour)MCMETours.tours.get(MCMETours.tourPlayers.get(p.getName()));
      t.teleportPlayer(p, s);
    }
  }
  
  public static void tourTpa(Player p)
  {
    if (MCMETours.tours.containsKey(p.getName()))
    {
      Tour t = (Tour)MCMETours.tours.get(p.getName());
      t.teleportAll(p);
    }
  }
}
