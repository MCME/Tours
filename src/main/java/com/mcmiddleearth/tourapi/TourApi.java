package com.mcmiddleearth.tourapi;

import com.mcmiddleearth.tourapi.tracker.PlayerTracker;
import com.mcmiddleearth.tourapi.utils.SaveTask;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * @author dags_ <dags@dags.me>
 */

public final class TourApi
{

    private static TourApiCore core;

    private TourApi()
    {}

    protected static TourApiCore getCore()
    {
        return core;
    }

    protected static Plugin getOwner()
    {
        return getCore().getPlugin();
    }

    public static void setApiCore(Plugin p)
    {
        if (core != null)
        {
            return;
        }
        core = new TourApiCore(p);
        getCore().getLogger().info("Initialized TourApi " + getVersion() + "!");
    }

    /*
     * Public static methods to be accessed by 3rd parties
     */

    /**
     * Get the data folder the TourApi's saved files
     * @return File data folder
     */
    public static File getDataFolder()
    {
        return getCore().getPlugin().getDataFolder();
    }

    /**
     * Get the current version of TourApi
     * @return String version
     */
    public static String getVersion()
    {
        return getCore().getVersion();
    }

    /**
     * Get the TourApi player tracker
     * @return PlayerTracker
     */
    public static PlayerTracker getPlayerTracker()
    {
        return getCore().getPlayerTracker();
    }

    /**
     * Check whether the given player is being tracked as a new player
     * @param p Player to check
     * @return boolean value
     */
    public static boolean isTrackingPlayer(Player p)
    {
        return getCore().getPlayerTracker().trackingPlayer(p);
    }

    /**
     * Get the date that the new player first connected to the server
     * @param p Player to check
     * @return String date, returns "null" if player is not being tracked
     */
    public static String getFirstJoinDate(Player p)
    {
        return getCore().getPlayerTracker().getJoinDate(p);
    }

    /**
     * Get the time that the new player first connected to the server
     * @param p Player to check
     * @return String time, returns "null" if player is not being tracked
     */
    public static String getFirstJoinTime(Player p)
    {
        return getCore().getPlayerTracker().getJoinTime(p);
    }

    /**
     * Save a configuration to the TourApi directory
     * @param c Configuration to save
     * @param fileName String file name
     */
    public static void saveConfig(Configuration c, String fileName)
    {
        if (c != null && fileName != null)
        {
            SaveTask st = new SaveTask(c, getDataFolder(), fileName);
            st.runTaskAsynchronously(getOwner());
        }
    }

}
