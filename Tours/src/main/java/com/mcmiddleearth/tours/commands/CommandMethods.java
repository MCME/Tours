package com.mcmiddleearth.tours.commands;

import com.mcmiddleearth.tours.tour.Tour;
import com.mcmiddleearth.tours.tour.TourManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.mcmiddleearth.tours.utils.Colors.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class CommandMethods
{

    private static StringBuilder sb = new StringBuilder();

    public static void tourInfo(Player p)
    {
        if (TourManager.getNumberOfTours() == 0)
        {
            p.sendMessage(yellow + "There are no tours running right now.");
            p.sendMessage(green + "Online Guides: ");
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
            for (String s : TourManager.getTours())
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
        if (!TourManager.hasTour(p.getName()))
        {
            if (!TourManager.playerIsTouring(p))
            {
                Tour t = TourManager.matchTour(s);
                if (t != null)
                {
                    t.addTourist(p);
                    t.teleportToLeader(p);
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
        if (!TourManager.hasTour(p.getName()))
        {
            if (TourManager.playerIsTouring(p))
            {
                Tour t = TourManager.getPlayerTour(p);
                if (t != null)
                {
                    t.removeTourist(p);
                }
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
        sb.append(gray).append("/tour join <GuideName> - join Guide's tour").append("\n");
        sb.append(gray).append("/tour leave - leave current tour").append("\n");
        if (!p.hasPermission("Tours.Ranger"))
        {
            sb.append(gray).append("/tourtp or /ttp - teleport to your tour guide").append("\n");
        }
        else if (p.hasPermission("Tours.ranger"))
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
        if (!TourManager.hasTour(p.getName()))
        {
            Tour t = TourManager.getPlayerTour(p);
            if (t != null)
            {
                t.removeTourist(p);
            }
            t = new Tour(p);
            TourManager.addTour(t);
            TourManager.setTourPlayer(p, t);
            t.tourNotify(green + "Tour started!");
            Bukkit.broadcastMessage(green + "Tour starting soon with " + aqua + p.getName());
            Bukkit.broadcastMessage(green + "Enter " + yellow + "/tour join " + p.getName() + green + " to join in!");
        }
        else
        {
            p.sendMessage(gray + "You are already leading a tour!");
        }
    }

    public static void tourStop(Player p)
    {
        Tour t = TourManager.getPlayerTour(p);
        if (t != null)
        {
            t.forceCloseTour(yellow + "Tour has ended!");
        }
    }

    public static void tourHat(Player p)
    {
        if (TourManager.hasTour(p.getName()))
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
        Tour t = TourManager.getTour(p.getName());
        if (t != null)
        {
            p.sendMessage(yellow + t.getTouristList().toString());
        }
    }

    public static void userTP(Player p)
    {
        Tour t = TourManager.getPlayerTour(p);
        if (t != null)
        {
            t.teleportToLeader(p);
        }
        else
        {
            p.sendMessage(gray + "You need to be participating in a tour to do that!");
        }
    }

    public static void tourTp(Player p, String s)
    {
        Tour t = TourManager.getPlayerTour(p);
        if (t != null)
        {
            t.teleportPlayer(p, s);
        }
    }

    public static void tourTpa(Player p)
    {
        if (TourManager.hasTour(p.getName()))
        {
            Tour t = TourManager.getTour(p.getName());
            if (t != null)
            {
                t.teleportAll(p);
            }
        }
    }

}
