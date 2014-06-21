package com.mcmiddleearth.tours.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author dags_ <dags@dags.me>
 */

public class Colors
{

    public final static String aqua = ChatColor.DARK_AQUA.toString();
    public final static String dGray = ChatColor.GRAY.toString();
    public final static String dPurple = ChatColor.DARK_PURPLE.toString();
    public final static String green = ChatColor.GREEN.toString();
    public final static String gray = ChatColor.GRAY.toString();
    public final static String lPurple = ChatColor.LIGHT_PURPLE.toString();
    public final static String yellow = ChatColor.YELLOW.toString();
    public final static String reset = ChatColor.RESET.toString();

    public static String getCol(String s)
    {
        String col;
        try
        {
            col = ChatColor.valueOf(s).toString();
        }
        catch (IllegalArgumentException e)
        {
            col = ChatColor.WHITE.toString();
            Bukkit.getLogger().info("[Tours] '" + s + "' is an invalid color! Defaulting to WHITE.");
        }
        return col;
    }

}
