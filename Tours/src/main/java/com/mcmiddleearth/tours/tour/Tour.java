package com.mcmiddleearth.tours.tour;

import com.mcmiddleearth.tourapi.TourApi;
import com.mcmiddleearth.tourapi.events.TourJoinEvent;
import com.mcmiddleearth.tourapi.events.TourLeaveEvent;
import com.mcmiddleearth.tourapi.events.TourStartEvent;
import com.mcmiddleearth.tourapi.events.TourStopEvent;
import com.mcmiddleearth.tours.Tours;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.mcmiddleearth.tours.utils.Colors.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class Tour
{

    private String leader;
    private List<String> tourists;

    public Tour(Player p)
    {
        leader = p.getName();
        tourists = new ArrayList<String>();
        tourists.add(p.getName());
        TourStartEvent tse = new TourStartEvent(p, leader);
        TourApi.callTourEvent(tse);
    }

    public String getTourName()
    {
        return leader;
    }

    public List<String> getTouristList()
    {
        return tourists;
    }

    public List<Player> getTourists()
    {
        List<Player> players = new ArrayList<Player>();
        for (String s : tourists)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op.isOnline())
            {
                players.add(op.getPlayer());
            }
        }
        return players;
    }

    public void addTourist(Player p)
    {
        if (!tourists.contains(p.getName()))
        {
            tourists.add(p.getName());
            TourManager.setTourPlayer(p, this);
            TourJoinEvent tje = new TourJoinEvent(p, leader, tourists.size());
            TourApi.callTourEvent(tje);

            tourNotify(yellow + "Everybody welcome " + green + p.getName() + yellow + " to the tour!");
            p.sendMessage(yellow + "For the best experience, join " + aqua + leader + yellow + " in TeamSpeak!");
        }
        else
        {
            p.sendMessage(gray + "You have already joined this tour!");
        }
    }

    public void removeTourist(Player p)
    {
        if (tourists.contains(p.getName()))
        {
            tourists.remove(p.getName());
            TourManager.removePlayer(p);
            TourLeaveEvent tle = new TourLeaveEvent(p, leader, tourists.size());
            TourApi.callTourEvent(tle);

            tourNotify(dGray + p.getName() + gray + " left the tour.");
            p.sendMessage(gray + "You left the tour!");
        }
        else
        {
            p.sendMessage(gray + "You are not part of any tours!");
        }
    }

    public void tourChat(Player p, String chat[])
    {
        String prefix;
        String message;

        if (p.hasPermission("Tours.ranger"))
        {
            prefix = "[" + aqua + "T" + reset + "] ";
            message = Tours.rangerChatColor + chat[1] + reset;
        }
        else
        {
            prefix = "[" + yellow + "T" + reset + "] ";
            message = Tours.userChatColor + chat[1] + reset;
        }

        for (String s : tourists)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op.isOnline())
            {
                op.getPlayer().sendMessage(chat[0] + prefix + message);
            }
        }
    }

    public void tourNotify(String alert)
    {
        for (String s : tourists)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op.isOnline())
            {
                op.getPlayer().sendMessage(alert);
            }
        }
    }

    public void teleportToLeader(Player p)
    {
        OfflinePlayer op = Bukkit.getOfflinePlayer(leader);
        if (op.isOnline())
        {
            Location l = op.getPlayer().getLocation();
            niceTp(l, p);
        }
    }

    public void teleportPlayer(Player p, String s)
    {
        Player target = p;

        for (String st : tourists)
        {
            if (st.toLowerCase().contains(s.toLowerCase()))
            {
                OfflinePlayer op = Bukkit.getOfflinePlayer(st);
                if (op.isOnline() && !st.equals(leader))
                {
                    target = (Player) op;
                    break;
                }
            }
        }

        if (!target.getName().equals(p.getName()))
        {
            Location l = p.getLocation();
            niceTp(l, target);
            p.sendMessage(dPurple + "User teleported!");
        }
        else
        {
            p.sendMessage(gray + "User not found on this tour!");
        }
    }

    public void teleportAll(Player p)
    {
        if (!tourists.isEmpty())
        {
            Location l = p.getLocation();

            for (String s : tourists)
            {
                OfflinePlayer op = Bukkit.getOfflinePlayer(s);
                {
                    if (op.isOnline())
                    {
                        Player q = op.getPlayer();
                        if (!q.getName().equals(p.getName()))
                        {
                            niceTp(l, q);
                        }
                    }
                }
            }
            p.sendMessage(dPurple + "Players teleported!");
        }
        else
        {
            p.sendMessage(gray + "No players to teleport!");
        }
    }

    private void niceTp(Location target, Player p)
    {
        target.setDirection(p.getLocation().getDirection());
        p.teleport(target);
        p.sendMessage(lPurple + "Teleported!");
    }

    public void forceCloseTour(String reason)
    {
        tourNotify(reason);
        tourClear();
    }

    public void softCloseTour(String reason)
    {
        for (String s : tourists)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op.isOnline())
            {
                Player p = op.getPlayer();
                if (p.hasPermission("Tours.cmd.ranger") && !s.equals(leader))
                {
                    tourNotify(yellow + "Your tour guide has switched to " + aqua + s);
                    switchLeader(p);
                    return;
                }
            }
        }
        tourNotify(reason);
        tourClear();
    }

    private void switchLeader(Player p)
    {
        final String name = p.getName();
        for (String s : tourists)
        {
            TourManager.setTourPlayer(s, name);
        }
        TourManager.removeTourByKey(getTourName());
        leader = name;
        TourManager.addTour(this);
    }

    public void tourClear()
    {
        TourStopEvent tse = new TourStopEvent(leader, tourists.size());
        TourApi.callTourEvent(tse);
        for (String s : tourists)
        {
            TourManager.removePlayerByName(s);
        }
        tourists.clear();
        TourManager.removeTourByKey(leader);
    }

}
