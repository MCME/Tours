package com.mcmiddleearth.tourapi.utils;

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

    private final File dataFolder;
    private final String name;
    private final FileConfiguration fc;

    public SaveTask(Configuration c, File folder, String fileName)
    {
        fc = (FileConfiguration) c;
        dataFolder = folder;
        name = fileName;
    }

    @Override
    public void run()
    {
        File f = new File(dataFolder, name);
        try
        {
            if (!f.exists())
            {
                if (f.createNewFile())
                {
                    System.out.println("[TourApi] Generating new file: " + name);
                }
            }
            fc.save(f);
        }
        catch (IOException e)
        {
            System.out.println("[TourApi] Could not save file: " + name);
        }
    }

}
