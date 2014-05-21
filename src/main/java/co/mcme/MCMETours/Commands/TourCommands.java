package co.mcme.MCMETours.Commands;

import co.mcme.MCMETours.Utils.Colors;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TourCommands
  implements CommandExecutor
{
  private static ChatColor error = ChatColor.GRAY;
  
  public boolean onCommand(CommandSender sender, Command command, String c, String[] a)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage(Colors.gray + "This command can only be run by a player!");
      return true;
    }
    Player p = (Player)sender;
    if (c.equalsIgnoreCase("tour"))
    {
      if (a.length == 0)
      {
        if (p.hasPermission("MCMETours.user"))
        {
          CommandMethods.tourInfo(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("request"))
      {
        if (p.hasPermission("MCMETours.user"))
        {
          CommandMethods.tourRequest(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("join"))
      {
        if (p.hasPermission("MCMETours.user"))
        {
          if (a.length == 1)
          {
            p.sendMessage(error + "Not enough arguments! /tour join <name>");
            return true;
          }
          CommandMethods.tourJoin(p, a[1]);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("leave"))
      {
        if (p.hasPermission("MCMETours.user"))
        {
          CommandMethods.tourLeave(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("help"))
      {
        if (p.hasPermission("MCMETours.user"))
        {
          CommandMethods.tourHelp(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("start"))
      {
        if (p.hasPermission("MCMETours.ranger"))
        {
          CommandMethods.tourStart(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("stop"))
      {
        if (p.hasPermission("MCMETours.ranger"))
        {
          CommandMethods.tourStop(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("hat"))
      {
        if (p.hasPermission("MCMETours.ranger"))
        {
          CommandMethods.tourHat(p);
          return true;
        }
        return nope(p);
      }
      if (a[0].equalsIgnoreCase("list"))
      {
        if (p.hasPermission("MCMETours.ranger"))
        {
          CommandMethods.tourList(p);
          return true;
        }
        return nope(p);
      }
    }
    if ((c.equalsIgnoreCase("tourtp")) || (c.equalsIgnoreCase("ttp")))
    {
      if (p.hasPermission("MCMETours.ranger"))
      {
        if (a.length == 1)
        {
          CommandMethods.tourTp(p, a[0]);
          return true;
        }
        p.sendMessage(error + "Incorrect use! /tourtp <player>");
        return true;
      }
      return nope(p);
    }
    if ((c.equalsIgnoreCase("tourtpa")) || (c.equalsIgnoreCase("ttpa"))) {
      if (p.hasPermission("MCMETours.ranger"))
      {
        if (a.length == 0)
        {
          CommandMethods.tourTpa(p);
          return true;
        }
      }
      else {
        return nope(p);
      }
    }
    return false;
  }
  
  private static boolean nope(Player p)
  {
    p.sendMessage(error + "Sorry you do not have permission to do that!");
    return true;
  }
}
