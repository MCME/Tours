package com.mcmiddleearth.tours.tour;

import com.mcmiddleearth.tours.Tours;
import static com.mcmiddleearth.tours.Tours.tourPlayers;
import static com.mcmiddleearth.tours.Tours.tours;
import static com.mcmiddleearth.tours.utils.Colors.*;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author dags_ <dags@dags.me>
 */
public class Tour
{

    private String leader;
    private String name;
    private List<String> tourists;
    public List<String> inChat;

    public boolean flightAllowed = true;

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
            p.sendMessage(yellow + "For the best experience, join " + aqua + leader + yellow + " in Discord!");
            if(!flightAllowed){
                p.setFlying(false);
                p.setAllowFlight(false);
            }
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

    public void kickPlayer(Player p,String s){
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
            removeTourist(target);
            p.sendMessage(dPurple + "User kicked!");
        }
        else
        {
            p.sendMessage(gray + "User not found on this tour!");
        }
    }

    public void giveRefreshments() {
        if (!tourists.isEmpty()) {
            for (String s : tourists) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(s);
                {
                    if (op.isOnline()) {
                        Player q = op.getPlayer();
                        ItemStack cookie = new ItemStack(Material.COOKIE,5);
                        ItemMeta cookie_meta = cookie.getItemMeta();
                        cookie_meta.setDisplayName("Biscuit");
                        cookie.setItemMeta(cookie_meta);
                        q.getInventory().addItem(cookie);
                        ItemStack tea = new ItemStack(Material.HONEY_BOTTLE,5);
                        ItemMeta tea_meta = tea.getItemMeta();
                        tea_meta.setDisplayName("Tea");
                        tea.setItemMeta(tea_meta);
                        q.getInventory().addItem(tea);
                    }
                }
            }
        }
    }


    public void toggleFly(Player p,boolean allowed){
        this.flightAllowed = allowed;

        if (!tourists.isEmpty())
        {
            if(!allowed) {
                for (String s : tourists) {
                    OfflinePlayer op = Bukkit.getOfflinePlayer(s);
                    {
                        if (op.isOnline()) {
                            Player q = op.getPlayer();
                            if (!q.getName().equals(p.getName())) {
                                q.setFlying(false);
                                q.setAllowFlight(false);
                            }
                        }
                    }
                }
            }
            if(allowed){
                p.sendMessage(dPurple + "Players are now allowed to fly.");
            }else{
                p.sendMessage(dPurple + "Players have to walk now.");
            }
        }
        else
        {
            p.sendMessage(gray + "No players to switch this!");
        }
    }

    public void playerToggleFlightFunc(PlayerToggleFlightEvent event) {
        if(!flightAllowed) {
            event.getPlayer().setFlying(false);
            event.getPlayer().setAllowFlight(false);
            event.setCancelled(true);
            sendFlightNotAllowed(event.getPlayer());
        }
    }

    private void sendFlightNotAllowed(Player player) {
        player.sendMessage("You are not allowed to fly in this tour.");
    }

}
