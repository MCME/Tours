/**
 * Tours is a light-weight tour management Bukkit plugin
 * Copyright (C) 2014  dags_
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.mcmiddleearth.tours;

import com.mcmiddleearth.tourapi.TourApi;
import com.mcmiddleearth.tours.commands.TourCommands;
import com.mcmiddleearth.tours.listeners.ChatListener;
import com.mcmiddleearth.tours.listeners.PlayerListener;
import com.mcmiddleearth.tours.tour.Tour;
import com.mcmiddleearth.tours.utils.Colors;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author dags_ <dags@dags.me>
 */
public class Tours extends JavaPlugin
{

    public static final HashMap<String, Tour> tours = new HashMap<String, Tour>();
    public static final HashMap<String, String> tourPlayers = new HashMap<String, String>();

    private static Tours instance;
    private static boolean tourChat;
    public static String userChatColor;
    public static String rangerChatColor;

    public Tours()
    {
        instance = this;
    }

    public static Tours inst()
    {
        if (instance == null)
        {
            instance = new Tours();
        }
        return instance;
    }

    @Override
    public void onEnable()
    {
        setupConfig();
        registerEvents();
        TourApi.setApiCore(this);
    }

    @Override
    public void onDisable()
    {
        tours.clear();
        tourPlayers.clear();
    }

    private void setupConfig()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
        tourChat = getConfig().getBoolean("ChatSettings.TourChat");
        userChatColor = Colors.getCol(getConfig().getString("ChatSettings.UserChat"));
        rangerChatColor = Colors.getCol(getConfig().getString("ChatSettings.RangerChat"));
    }

    private void registerEvents()
    {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        if (tourChat)
        {
            pm.registerEvents(new ChatListener(), this);
        }
        TourCommands tourCommands = new TourCommands();
        getCommand("tour").setExecutor(tourCommands);
        getCommand("tourtpa").setExecutor(tourCommands);
        getCommand("tourtp").setExecutor(tourCommands);
        getCommand("ttpa").setExecutor(tourCommands);
        getCommand("ttp").setExecutor(tourCommands);
    }

}

