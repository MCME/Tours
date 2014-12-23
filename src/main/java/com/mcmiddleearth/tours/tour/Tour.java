package com.mcmiddleearth.tours.tour;

import com.mcmiddleearth.tours.Tours;
import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;
import static com.mcmiddleearth.tours.utils.Colors.*;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author dags_ <dags@dags.me>
 */
public class Tour
{

    private String leader;
    private String name;
    private List<String> tourists;
    public List<String> inChat;

    public Tour(Player p)
    {
        leader = p.getName();
        name = leader;
        tourists = new ArrayList<String>();
        tourists.add(p.getName());
        inChat = new ArrayList<String>();
        inChat.add(p.getName());
    }

    public void addTourist(Player p)
    {
        if (!tourists.contains(p.getName()))
        {
            tourists.add(p.getName());
            inChat.add(p.getName());
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
            inChat.remove(p.getName());

            String alert = dGray + p.getName() + gray + " left the tour.";
            tourNotify(alert);
            p.sendMessage(gray + "You left the tour!");
        }
        else
        {
            p.sendMessage(gray + "You are not part of any tours!");
        }
    }

    public String getTourName()
    {
        return name;
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
    
    public List<Player> getInChat()
    {
        List<Player> players = new ArrayList<Player>();

        for (String s : inChat)
        {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            if (op.isOnline())
            {
                players.add(op.getPlayer());
            }
        }

        return players;
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

    public void tourClear()
    {
        for (String p : tourists){
            tourPlayers.remove(p);
        }
        tourists.clear();
        tours.remove(getTourName());
    }

    public void teleportToLeader(Player p)
    {
        OfflinePlayer op = Bukkit.getOfflinePlayer(name);
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

}
