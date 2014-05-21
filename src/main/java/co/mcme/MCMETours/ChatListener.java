package co.mcme.MCMETours;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener
  implements Listener
{
  @EventHandler(priority=EventPriority.HIGH)
  public void PlayerChat(AsyncPlayerChatEvent event)
  {
    if (!event.isCancelled())
    {
      String[] raw = event.getFormat().split(" ");
      String name = getDispName(raw);
      Player p = event.getPlayer();
      if (CommandMethods.group.containsKey(p))
      {
        if (MCMETours.logchat)
        {
          Date d = new Date();
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          
          String t = df.format(d);
          String pre = "[" + (String)CommandMethods.group.get(p) + "]";
          String msg = t + pre + p.getName() + ": " + event.getMessage();
          logToFile(msg);
        }
        for (Player q : Bukkit.getServer().getOnlinePlayers())
        {
          if ((CommandMethods.group.get(p) == CommandMethods.group.get(q)) && (!p.hasPermission("MCMETours.ranger")))
          {
            q.sendMessage(name + "[" + ChatColor.YELLOW + "T" + ChatColor.WHITE + "] " + MCMETours.userChatColor + event.getMessage());
            
            event.setCancelled(true);
          }
          if ((CommandMethods.group.get(p) == CommandMethods.group.get(q)) && (p.hasPermission("MCMETours.ranger")))
          {
            q.sendMessage(name + "[" + ChatColor.DARK_AQUA + "T" + ChatColor.WHITE + "] " + MCMETours.rangerChatColor + event.getMessage());
            

            event.setCancelled(true);
          }
          if ((!CommandMethods.group.containsKey(q)) || (CommandMethods.group.get(p) != CommandMethods.group.get(q))) {
            q.sendMessage(name + event.getMessage());
          }
        }
      }
    }
  }
  
  private static String getDispName(String[] raw)
  {
    String name = "";
    int i = 0;
    while (i < raw.length - 1)
    {
      name = name + raw[i] + " ";
      i++;
    }
    return name;
  }
  
  public void logToFile(String message)
  {
    try
    {
      File saveTo = new File("plugins/MCMETours/", "TourChat.log");
      if (!saveTo.exists()) {
        saveTo.createNewFile();
      }
      FileWriter fw = new FileWriter(saveTo, true);
      PrintWriter pw = new PrintWriter(fw);
      pw.println(message);
      pw.flush();
      pw.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
