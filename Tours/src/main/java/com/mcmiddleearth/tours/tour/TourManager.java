package com.mcmiddleearth.tours.tour;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

/**
 * @author dags_ <dags@dags.me>
 */

public class TourManager
{

    private static TourManager instance;
    private HashMap<String, String> tourPlayers;
    private HashMap<String, Tour> tours;

    private TourManager()
    {
        instance = this;
        tourPlayers = new HashMap<String, String>();
        tours = new HashMap<String, Tour>();
    }

    public static Set<String> getTours()
    {
        return inst().tours.keySet();
    }

    private static TourManager inst()
    {
        if (instance == null)
        {
            instance = new TourManager();
        }
        return instance;
    }

    public static Tour getTour(String s)
    {
        if (inst().tours.containsKey(s))
        {
            return inst().tours.get(s);
        }
        return null;
    }

    public static Tour getPlayerTour(Player p)
    {
        if (inst().tourPlayers.containsKey(p.getName()))
        {
            String tourName = inst().tourPlayers.get(p.getName());
            if (inst().tours.containsKey(tourName))
            {
                return inst().tours.get(tourName);
            }
        }
        return null;
    }

    public static Tour matchTour(String s)
    {
        String match = s.toLowerCase();
        for (String t : inst().tours.keySet())
        {
            if (t.toLowerCase().contains(match))
            {
                return getTour(t);
            }
        }
        return null;
    }

    public static boolean playerIsTouring(Player p)
    {
        return inst().tourPlayers.containsKey(p.getName());
    }

    public static boolean hasTour(String s)
    {
        return inst().tours.containsKey(s);
    }

    public static int getNumberOfTours()
    {
        return inst().tours.size();
    }

    public static void addTour(Tour t)
    {
        inst().tours.put(t.getTourName(), t);
    }

    public static void removeTourByKey(String s)
    {
        if (inst().tours.containsKey(s))
        {
            inst().tours.remove(s);
        }
    }

    public static void setTourPlayer(Player p, Tour t)
    {
        inst().tourPlayers.put(p.getName(), t.getTourName());
    }

    public static void setTourPlayer(String s, String t)
    {
        inst().tourPlayers.put(s, t);
    }

    public static void removePlayer(Player p)
    {
        inst().rmPlayer(p.getName());
    }

    public static void removePlayerByName(String s)
    {
        inst().rmPlayer(s);
    }

    private void rmPlayer(String name)
    {
        if (tourPlayers.containsKey(name))
        {
            tourPlayers.remove(name);
        }
    }

    public static void clear()
    {
        inst().tours.clear();
        inst().tourPlayers.clear();
    }

}
