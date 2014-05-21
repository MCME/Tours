package co.mcme.MCMETours;

import co.mcme.MCMETours.Commands.TourCommands;
import co.mcme.MCMETours.Listeners.ChatListener;
import co.mcme.MCMETours.Listeners.PlayerListener;
import co.mcme.MCMETours.Tour.Tour;
import co.mcme.MCMETours.Utils.Colors;
import java.util.HashMap;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCMETours
  extends JavaPlugin
{
  public static HashMap<String, Tour> tours = new HashMap();
  public static HashMap<String, String> tourPlayers = new HashMap();
  private static boolean tourChat;
  public static String userChatColor;
  public static String rangerChatColor;
  
  public void onEnable()
  {
    setupConfig();
    registerEvents();
  }
  
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
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new PlayerListener(), this);
    if (tourChat) {
      pm.registerEvents(new ChatListener(), this);
    }
    getCommand("tour").setExecutor(new TourCommands());
    getCommand("tourtpa").setExecutor(new TourCommands());
    getCommand("tourtp").setExecutor(new TourCommands());
    getCommand("ttpa").setExecutor(new TourCommands());
    getCommand("ttp").setExecutor(new TourCommands());
  }
}
