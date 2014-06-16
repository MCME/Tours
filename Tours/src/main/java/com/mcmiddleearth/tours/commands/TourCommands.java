package com.mcmiddleearth.tours.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mcmiddleearth.tours.utils.Colors.gray;

/**
 * @author dags_ <dags@dags.me>
 */
public class TourCommands implements CommandExecutor
{

    private static ChatColor error = ChatColor.GRAY;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String c, String[] a)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(gray + "This command can only be run by a player!");
            return true;
        }
        else
        {
            Player p = (Player) sender;

            if (c.equalsIgnoreCase("tour"))
            {
                if (a.length == 0)
                {
                    if (p.hasPermission("Tours.cmd.user"))
                    {
                        CommandMethods.tourInfo(p);
                        return true;
                    }
                    return nope(p);
                }
                if (a[0].equalsIgnoreCase("request"))
                {
                    if (p.hasPermission("Tours.cmd.user"))
                    {
                        CommandMethods.tourRequest(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("join"))
                {
                    if (p.hasPermission("Tours.cmd.user"))
                    {
                        if (a.length == 1)
                        {
                            p.sendMessage(error + "Not enough arguments! /tour join <name>");
                            return true;
                        }
                        else
                        {
                            CommandMethods.tourJoin(p, a[1]);
                            return true;
                        }
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("leave"))
                {
                    if (p.hasPermission("Tours.cmd.user"))
                    {
                        CommandMethods.tourLeave(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("help"))
                {
                    if (p.hasPermission("Tours.cmd.user"))
                    {
                        CommandMethods.tourHelp(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("start"))
                {
                    if (p.hasPermission("Tours.cmd.ranger"))
                    {
                        CommandMethods.tourStart(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("stop"))
                {
                    if (p.hasPermission("Tours.cmd.ranger"))
                    {
                        CommandMethods.tourStop(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("hat"))
                {
                    if (p.hasPermission("Tours.cmd.ranger"))
                    {
                        CommandMethods.tourHat(p);
                        return true;
                    }
                    return nope(p);
                }
                else if (a[0].equalsIgnoreCase("list"))
                {
                    if (p.hasPermission("Tours.cmd.ranger"))
                    {
                        CommandMethods.tourList(p);
                        return true;
                    }
                    return nope(p);
                }
            }
            if (c.equalsIgnoreCase("tourtp") || c.equalsIgnoreCase("ttp"))
            {
                if (p.hasPermission("Tours.cmd.ranger"))
                {
                    if (a.length == 1)
                    {
                        CommandMethods.tourTp(p, a[0]);
                        return true;
                    }
                    p.sendMessage(gray + "/ttp <Player>");
                    return true;
                }
                else if (p.hasPermission("Tours.cmd.user"))
                {
                    CommandMethods.userTP(p);
                    return true;
                }
                return nope(p);
            }
            else if (c.equalsIgnoreCase("tourtpa") || c.equalsIgnoreCase("ttpa"))
            {
                if (p.hasPermission("Tours.cmd.ranger"))
                {
                    if (a.length == 0)
                    {
                        CommandMethods.tourTpa(p);
                        return true;
                    }
                }
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
