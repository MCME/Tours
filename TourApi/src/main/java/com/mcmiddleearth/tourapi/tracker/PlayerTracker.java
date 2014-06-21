package com.mcmiddleearth.tourapi.tracker;

import com.mcmiddleearth.tourapi.TourApi;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author dags_ <dags@dags.me>
 */
public final class PlayerTracker
{

    private final String file = "newplayers.yml";
    private final Configuration conf;

    public PlayerTracker()
    {
        conf = loadPlayers();
        save();
    }

    private Configuration loadPlayers()
    {
        File f = new File(TourApi.getDataFolder(), file);
        if (f.exists())
        {
            return YamlConfiguration.loadConfiguration(f);
        }
        return new YamlConfiguration();
    }

    /**
     * Adds the player to be tracked, storing their first-join date and time
     * TourApi handles the adding of players automatically, but players can also be added manually via this method
     * @param p Player to be tracked
     * @param date String date
     * @param time String time
     */
    public void addNewPlayer(Player p, String date, String time)
    {
        String path = "TrackedPlayers." + p.getUniqueId().toString();
        conf.set(path + ".name", p.getName());
        conf.set(path + ".joined.date", date);
        conf.set(path + ".joined.time", time);
        save();
    }

    /**
     * Check whether TourApi is tracking the given player
     * @param p Player to check
     * @return boolean - true if this player is a new player being tracked
     */
    public boolean trackingPlayer(Player p)
    {
        return conf.get("TrackedPlayers." + p.getUniqueId().toString()) != null;
    }

    /**
     * Get the date that the given player first connected to the server
     * @param p Player to retrieve the join date for
     * @return String date. Returns "null" if the player is not being tracked
     */
    public String getJoinDate(Player p)
    {
        return conf.getString("TrackedPlayers." + p.getUniqueId().toString() + ".joined.date", "null");
    }

    /**
     * Get the time that the given player first connected to the server
     * @param p Player to retrieve the join time for
     * @return String time. Returns "null" if the player is not being tracked
     */
    public String getJoinTime(Player p)
    {
        return conf.getString("TrackedPlayers." + p.getUniqueId().toString() + ".joined.time", "null");
    }

    /**
     * Remove the given player from the PlayerTracker
     * TourApi handles the removal of players automatically, but players can also be removed manually via this method
     * @param p Player to be removed
     */
    public void removePlayer(Player p)
    {
        conf.set("TrackedPlayers." + p.getUniqueId().toString(), null);
        save();
    }

    private void save()
    {
        TourApi.saveConfig(conf, file);
    }

}
