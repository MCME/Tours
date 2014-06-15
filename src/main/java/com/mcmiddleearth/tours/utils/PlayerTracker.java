package com.mcmiddleearth.tours.utils;

import com.mcmiddleearth.tours.Tours;
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
        File f = new File(Tours.inst().getDataFolder(), file);
        if (f.exists())
        {
            return YamlConfiguration.loadConfiguration(f);
        }
        return new YamlConfiguration();
    }

    public synchronized void addNewPlayer(Player p)
    {
        conf.set("TrackedPlayers." + p.getUniqueId().toString(), p.getName());
        save();
    }

    public synchronized boolean isTrackingPlayer(Player p)
    {
        return conf.get("TrackedPlayers." + p.getUniqueId().toString()) != null;
    }

    public synchronized void removePlayer(Player p)
    {
        conf.set("TrackedPlayers." + p.getUniqueId().toString(), null);
        save();
    }

    public synchronized void save()
    {
        if (conf != null)
        {
            SaveTask st = new SaveTask(conf, file);
            st.runTaskAsynchronously(Tours.inst());
        }
    }

}
