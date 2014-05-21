package co.mcme.MCMETours.Tour;

import co.mcme.MCMETours.MCMETours;
import co.mcme.MCMETours.Utils.Colors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Tour
{
  private String leader;
  private String name;
  private List<String> tourists;
  
  public Tour(Player p)
  {
    this.leader = p.getName();
    this.name = this.leader;
    this.tourists = new ArrayList();
    this.tourists.add(p.getName());
  }
  
  public void addTourist(Player p)
  {
    if (!this.tourists.contains(p.getName()))
    {
      this.tourists.add(p.getName());
      String alert = Colors.yellow + "Everybody welcome " + Colors.green + p.getName() + Colors.yellow + " to the tour!";
      tourNotify(alert);
    }
    else
    {
      p.sendMessage(Colors.gray + "You have already joined this tour!");
    }
  }
  
  public void removeTourist(Player p)
  {
    if (this.tourists.contains(p.getName()))
    {
      this.tourists.remove(p.getName());
      
      String alert = Colors.dGray + p.getName() + Colors.gray + " left the tour.";
      tourNotify(alert);
      p.sendMessage(Colors.gray + "You left the tour!");
    }
    else
    {
      p.sendMessage(Colors.gray + "You are not part of any tours!");
    }
  }
  
  public String getTourName()
  {
    return this.name;
  }
  
  public List<String> getTouristList()
  {
    return this.tourists;
  }
  
  public List<Player> getTourists()
  {
    List<Player> players = new ArrayList();
    for (String s : this.tourists)
    {
      OfflinePlayer op = Bukkit.getOfflinePlayer(s);
      if (op.isOnline()) {
        players.add((Player)op);
      }
    }
    return players;
  }
  
  public void tourChat(Player p, String[] chat)
  {
    String message;
    String prefix;
    String message;
    if (p.hasPermission("MCMETours.ranger"))
    {
      String prefix = "[" + Colors.aqua + "T" + Colors.reset + "] ";
      message = MCMETours.rangerChatColor + chat[1] + Colors.reset;
    }
    else
    {
      prefix = "[" + Colors.yellow + "T" + Colors.reset + "] ";
      message = MCMETours.userChatColor + chat[1] + Colors.reset;
    }
    for (String s : this.tourists)
    {
      OfflinePlayer op = Bukkit.getOfflinePlayer(s);
      if (op.isOnline()) {
        ((Player)op).sendMessage(chat[0] + prefix + message);
      }
    }
  }
  
  public void tourNotify(String alert)
  {
    for (String s : this.tourists)
    {
      OfflinePlayer op = Bukkit.getOfflinePlayer(s);
      if (op.isOnline()) {
        ((Player)op).sendMessage(alert);
      }
    }
  }
  
  public void tourClear()
  {
    this.tourists.clear();
    MCMETours.tours.remove(getTourName());
  }
  
  public void teleportPlayer(Player p, String s)
  {
    Player target = p;
    for (String st : this.tourists) {
      if (st.toLowerCase().contains(s.toLowerCase()))
      {
        OfflinePlayer op = Bukkit.getOfflinePlayer(st);
        if ((op.isOnline()) && (!st.equals(this.leader)))
        {
          target = (Player)op;
          break;
        }
      }
    }
    if (!target.getName().equals(p.getName()))
    {
      Location l = p.getLocation();
      
      target.teleport(l);
      target.sendMessage(Colors.lPurple + "Teleported!");
      p.sendMessage(Colors.dPurple + "User teleported!");
    }
    else
    {
      p.sendMessage(Colors.gray + "User not found on this tour!");
    }
  }
  
  public void teleportAll(Player p)
  {
    if (!this.tourists.isEmpty())
    {
      Location l = p.getLocation();
      for (String s : this.tourists)
      {
        OfflinePlayer op = Bukkit.getOfflinePlayer(s);
        if (op.isOnline())
        {
          Player q = (Player)op;
          if (!q.getName().equals(p.getName()))
          {
            q.teleport(l);
            q.sendMessage(Colors.lPurple + "Teleported!");
          }
        }
      }
      p.sendMessage(Colors.dPurple + "Players teleported!");
    }
    else
    {
      p.sendMessage(Colors.gray + "No players to teleport!");
    }
  }
}
