package com.mcmiddleearth.tours.utils;

import com.mcmiddleearth.tours.Tours;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

/**
 * @author dags_ <dags@dags.me>
 */
public class SaveTask extends BukkitRunnable
{

    private final String name;
    private final FileConfiguration fc;

    public SaveTask(Configuration c, String fileName)
    {
        name = fileName;
        fc = (FileConfiguration) c;
    }

    @Override
    public void run()
    {
        File f = new File(Tours.inst().getDataFolder(), name);
        try
        {
            if (!f.exists())
            {
                if (f.createNewFile())
                {
                    Tours.inst().getLogger().info("Generating new file: " + name);
                }
            }
            fc.save(f);
        }
        catch (IOException e)
        {
            Tours.inst().getLogger().warning("Could not save file: " + name);
        }
    }

}
