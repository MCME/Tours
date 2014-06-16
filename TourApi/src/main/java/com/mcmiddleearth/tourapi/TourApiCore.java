package com.mcmiddleearth.tourapi;

import com.mcmiddleearth.tourapi.eventtriggers.NewPlayerEventTrigger;
import com.mcmiddleearth.tourapi.tracker.PlayerTracker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.logging.Logger;

/**
 * @author dags_ <dags@dags.me>
 */

final class TourApiCore
{

    private Plugin owner;
    private PlayerTracker playerTracker;
    private String version;

    protected TourApiCore(Plugin p)
    {
        if (owner != null)
        {
            return;
        }
        owner = p;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new NewPlayerEventTrigger(p), p);
    }

    protected Plugin getPlugin()
    {
        return owner;
    }

    protected String getVersion()
    {
        if (version == null)
        {
            version = "1.0.0";
        }
        return version;
    }

    protected Logger getLogger()
    {
        return owner.getLogger();
    }

    protected PlayerTracker getPlayerTracker()
    {
        if (playerTracker == null)
        {
            playerTracker = new PlayerTracker();
        }
        return playerTracker;
    }

}
