package com.mcmiddleearth.tours.utils;

import org.bukkit.entity.Player;

import com.mcmiddleearth.tours.Tours;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

/**
 * @author kiyz
 */
public class DiscordHandler {
	
	public static void tourStartDiscordMessage(Player p) 
	{
            discordMessage("!\n :ringmcme: **" + p.getName() + "** is starting a tour! :ringmcme:" +
				"\nTo join the tour type this in game chat: ```css\n/tour join " + p.getName() + "```");
	}
	
	private static void discordMessage(String message) 
	{
		DiscordSRV discordPlugin = DiscordSRV.getPlugin();
		TextChannel channel = discordPlugin.getDestinationTextChannelForGameChannelName((Tours.getDiscordChannel()));
		DiscordUtil.sendMessage(channel, message, 0, false);
	}

}
