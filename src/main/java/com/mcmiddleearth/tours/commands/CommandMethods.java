package com.mcmiddleearth.tours.commands;

import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;

import com.mcmiddleearth.tours.Tours;
import com.mcmiddleearth.tours.tour.Tour;
import com.mcmiddleearth.tours.utils.DiscordHandler;

import static com.mcmiddleearth.tours.utils.Colors.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author dags_ <dags@dags.me>
 */
public class CommandMethods
{

    private static StringBuilder sb = new StringBuilder();

    public static void tourInfo(Player p)
    {
        if (tours.isEmpty())
        {
            p.sendMessage(yellow + "There are no tours running right now.");
            p.sendMessage(green + "Online Rangers: ");
            for (Player q : Bukkit.getOnlinePlayers())
            {
                if (q.hasPermission("Tours.ranger") && !q.hasPermission("Tours.admin"))
                {
                    p.sendMessage(aqua + q.getName());
                }
            }
        }
        else
        {
            p.sendMessage(green + "Currently running tours:");
            for (String s : tours.keySet())
            {
                p.sendMessage(aqua + s);
            }
        }
    }

    public static void tourRequest(Player p)
    {
        p.sendMessage(yellow + "Request sent!");
        Bukkit.getServer().broadcast(green + p.getName() + yellow + " requests a tour!", "Tours.notify.request");
    }

    public static void tourJoin(Player p, String s)
    {
        if (!tours.keySet().contains(p.getName()))
        {
            if (!tourPlayers.containsKey(p.getName()))
            {
                String tourName = "null";
                for (String st : tours.keySet())
                {
                    if (st.toLowerCase().contains(s.toLowerCase()))
                    {
                        tourName = st;
                        break;
                    }
                }

                if (!tourName.equals("null"))
                {
                    Tour t = tours.get(tourName);

                    t.addTourist(p);
                    t.teleportToLeader(p);
                    tourPlayers.put(p.getName(), t.getTourName());
                }
            }
            else
            {
                p.sendMessage(gray + "You are already on a tour! You must leave it first.");
            }
        }
        else
        {
            p.sendMessage(gray + "You are currently running a tour! You must stop it first.");
        }
    }

    public static void tourLeave(Player p)
    {
        if (!tours.keySet().contains(p.getName()))
        {
            if (tourPlayers.containsKey(p.getName()))
            {
                Tour t = tours.get(tourPlayers.get(p.getName()));
                t.removeTourist(p);

                tourPlayers.remove(p.getName());
            }
            else
            {
                p.sendMessage(gray + "You are not currently on a tour!");
            }
        }
        else
        {
            p.sendMessage(gray + "You are currently running a tour! You must stop it first.");
        }
    }

    public static void tourHelp(Player p)
    {
        sb.setLength(0);
        sb.append(gray).append("/tour - check for tour information").append("\n");
        sb.append(gray).append("/tour request - submit a request for a tour").append("\n");
        sb.append(gray).append("/tour join <RangerName> - join Ranger's tour").append("\n");
        sb.append(gray).append("/tour leave - leave current tour").append("\n");
        if (!p.hasPermission("Tours.Ranger"))
        {
            sb.append(gray).append("/tourtp or /ttp - teleport to your tour guide").append("\n");
        }
        else if (p.hasPermission("Tours.cmd.ranger"))
        {
            sb.append(gray).append("/tour start - host a tour").append("\n");
            sb.append(gray).append("/tour stop - stop your tour").append("\n");
            sb.append(gray).append("/tour hat - wear a Glowstone hat").append("\n");
            sb.append(gray).append("/tour list - list users on your tour").append("\n");
            sb.append(gray).append("/tourtp <playername> - tp player on your tour to you").append("\n");
            sb.append(gray).append("/tourtpa - tp all players on your tour to you").append("\n");
        }
        p.sendMessage(sb.toString());
    }

    public static void tourStart(Player p)
    {
        if (!tours.containsKey(p.getName()))
        {
            Tour t;
            if (tourPlayers.containsKey(p.getName()))
            {
                t = tours.get(tourPlayers.get(p.getName()));
                t.removeTourist(p);
            }

            t = new Tour(p);
            tours.put(t.getTourName(), t);
            t.tourNotify(green + "Tour started!");
            Bukkit.broadcastMessage(green + "Tour starting soon with " + aqua + p.getName());
            Bukkit.broadcastMessage(green + "Enter " + yellow + "/tour join " + p.getName() + green + " to join in!");
            tourPlayers.put(p.getName(), t.getTourName());
            if (Tours.discordMessage) DiscordHandler.tourStartDiscordMessage(p);
        }
        else
        {
            p.sendMessage(gray + "You are already leading a tour!");
        }
    }

    public static void tourStop(Player p)
    {
        if (tours.containsKey(p.getName()))
        {
            Tour t = tours.get(p.getName());
            t.tourNotify(yellow + "Tour has ended!");
            t.tourClear();
            tourPlayers.remove(p.getName());
        }
    }

	public static void tourHat(Player p)
    {
        if (tours.containsKey(p.getName()))
        {
            if (!p.getItemInHand().getType().equals(Material.AIR))
            {
                p.getInventory().setHelmet(new ItemStack(p.getItemInHand().getType()));
                p.sendMessage(green + "Hat added!");
            }
            else
            {
                p.getInventory().setHelmet(null);
                p.sendMessage(gray + "Hat removed!");
            }
        }
        else
        {
            p.sendMessage(gray + "You need to be leading a tour to use that!");
        }
    }

    public static void tourList(Player p)
    {
        if (tours.containsKey(p.getName()))
        {
            Tour t = tours.get(p.getName());
            p.sendMessage(yellow + t.getTouristList().toString());
        }
    }

    public static void userTP(Player p)
    {
        if (tourPlayers.containsKey(p.getName()))
        {
            Tour t = tours.get(tourPlayers.get(p.getName()));
            t.teleportToLeader(p);
        }
        else
        {
            p.sendMessage(gray + "You need to be participating in a tour to do that!");
        }
    }

    public static void tourTp(Player p, String s)
    {
        if (tourPlayers.containsKey(p.getName()))
        {
            Tour t = tours.get(tourPlayers.get(p.getName()));
            t.teleportPlayer(p, s);
        }
    }

    public static void tourTpa(Player p)
    {
        if (tours.containsKey(p.getName()))
        {
            Tour t = tours.get(p.getName());
            t.teleportAll(p);
        }
    }
    
    public static void tourChatToggle(Player p)
    {
        if (tourPlayers.containsKey(p.getName()))
        {
            Tour t = tours.get(tourPlayers.get(p.getName()));
            
            if (t.getInChat().contains(p))
            {
                t.inChat.remove(p.getName());
                p.sendMessage(yellow + "You are now in public chat! Nobody else in the tour will hear you.");
            }
            else
            {
                t.inChat.add(p.getName());
                p.sendMessage(yellow + "You are now in tour chat! People outside the tour cannot hear you.");
            }
        }
    }

    public static void switchFly(Player p, boolean allowed){
        if (tours.containsKey(p.getName()))
        {
            Tour t = tours.get(p.getName());
            t.toggleFly(p,allowed);
        }
    }

}
