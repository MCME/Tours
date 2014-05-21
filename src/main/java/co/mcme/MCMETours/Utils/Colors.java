package co.mcme.MCMETours.Utils;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Colors
{
  public static final String aqua = ChatColor.DARK_AQUA.toString();
  public static final String dGray = ChatColor.GRAY.toString();
  public static final String dPurple = ChatColor.DARK_PURPLE.toString();
  public static final String green = ChatColor.GREEN.toString();
  public static final String gray = ChatColor.GRAY.toString();
  public static final String lPurple = ChatColor.LIGHT_PURPLE.toString();
  public static final String yellow = ChatColor.YELLOW.toString();
  public static final String reset = ChatColor.RESET.toString();
  
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
      Bukkit.getLogger().info("[MCMETours] '" + s + "' is an invalid color! Defaulting to WHITE.");
    }
    return col;
  }
}
