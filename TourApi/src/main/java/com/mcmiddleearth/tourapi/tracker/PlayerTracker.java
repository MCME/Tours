package com.mcmiddleearth.tourapi.tracker;

import com.mcmiddleearth.tourapi.TourApi;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author dags_ <dags@dags.me>
 */
public class PlayerTracker
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

    public void addNewPlayer(Player p, String date, String time)
    {
        String path = "TrackedPlayers." + p.getUniqueId().toString();
        conf.set(path + ".name", p.getName());
        conf.set(path + ".joined.date", date);
        conf.set(path + ".joined.time", time);
        save();
    }

    public boolean trackingPlayer(Player p)
    {
        return conf.get("TrackedPlayers." + p.getUniqueId().toString()) != null;
    }

    public String getJoinDate(Player p)
    {
        return conf.getString("TrackedPlayers." + p.getUniqueId().toString() + ".joined.date", "null");
    }

    public String getJoinTime(Player p)
    {
        return conf.getString("TrackedPlayers." + p.getUniqueId().toString() + ".joined.time", "null");
    }

    public void removePlayer(Player p)
    {
        conf.set("TrackedPlayers." + p.getUniqueId().toString(), null);
        save();
    }

    public void save()
    {
        TourApi.saveConfig(conf, file);
    }

}
