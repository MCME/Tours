package com.mcmiddleearth.tours.commands;

import static com.mcmiddleearth.tours.utils.Colors.gray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 * @author dags_ <dags@dags.me>
 */
public class TourCommands implements TabExecutor
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
        else {
            Player p = (Player) sender;

            if (c.equalsIgnoreCase("tour")) {
                if (a.length == 0) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        CommandMethods.tourInfo(p);
                        return true;
                    }
                    return nope(p);
                }
                if (a[0].equalsIgnoreCase("request")) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        CommandMethods.tourRequest(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("join")) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        if (a.length == 1) {
                            p.sendMessage(error + "Not enough arguments! /tour join <name>");
                            return true;
                        } else {
                            CommandMethods.tourJoin(p, a[1]);
                            return true;
                        }
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("leave")) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        CommandMethods.tourLeave(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("help")) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        CommandMethods.tourHelp(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("start")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        CommandMethods.tourStart(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("stop")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        CommandMethods.tourStop(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("hat")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        CommandMethods.tourHat(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("kick")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        if (a.length == 1) {
                            p.sendMessage(error + "Not enough arguments! /tour kick <name>");
                            return true;
                        } else {
                            CommandMethods.kickPlayer(p, a[1]);
                            return true;
                        }
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("refreshments")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        CommandMethods.giveRefreshments(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("list")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        CommandMethods.tourList(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("chat")) {
                    if (p.hasPermission("Tours.cmd.user")) {
                        CommandMethods.tourChatToggle(p);
                        return true;
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("deny")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        if (a.length == 1) {
                            p.sendMessage(error + "Not enough arguments! /tour deny <name>");
                            return true;
                        } else {
                            if (a[1].equalsIgnoreCase("fly")) {
                                CommandMethods.switchFly(p, false);
                                return true;
                            }
                        }
                    }
                    return nope(p);
                } else if (a[0].equalsIgnoreCase("allow")) {
                    if (p.hasPermission("Tours.cmd.ranger")) {
                        if (a.length == 1) {
                            p.sendMessage(error + "Not enough arguments! /tour allow <name>");
                            return true;
                        } else {
                            if (a[1].equalsIgnoreCase("fly")) {
                                CommandMethods.switchFly(p, true);
                                return true;
                            }
                        }
                    }
                }
                return nope(p);
            }
            if (c.equalsIgnoreCase("tourtp") || c.equalsIgnoreCase("ttp"))
            {
                if (p.hasPermission("Tours.cmd.ranger"))
                {
                    if (a.length == 1)
                    {
                        CommandMethods.tourTp(p, a[0]);
                        return true;
                    }else if(a.length == 0){
                        CommandMethods.userTP(p);
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
    
    @Override
    public List<String> onTabComplete (CommandSender sender, Command command, String alias, String[] args)
    {
        if (command.getName().equalsIgnoreCase("tour"))
        {
            if (args.length == 1)
            {
                List<String> tabs = new ArrayList<String> ();
                if (sender.hasPermission("Tours.cmd.user"))
                {
                    tabs = new ArrayList<String> (Arrays.asList(new String[] {"join", "leave", "help", "chat"}));
                }
                if (sender.hasPermission("Tours.cmd.ranger"))
                {
                    tabs.addAll(Arrays.asList(new String[] {"start", "stop", "list", "hat"}));
                }
                return tabs;
            }
        }
        return null;
        
    }

}
