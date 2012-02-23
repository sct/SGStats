package com.sgcraft.sgstats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.sgcraft.sgstats.Achievement;
import com.sgcraft.sgstats.Category;
import com.sgcraft.sgstats.PlayerStat;
import com.sgcraft.sgstats.SGStats;

public class StatCommands implements CommandExecutor {
	public static SGStats plugin;
	
	public StatCommands (SGStats instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (args[0].equalsIgnoreCase("blocks")) {
			PlayerStat ps = SGStats.stats.get(sender.getName());
			plugin.log.info("[SGStats] [Debug] Got playerstat for player: " + ps.player.getName());
			Category cat = ps.get("blockdestroy");
			for (String key : cat.getEntries()) {
				sender.sendMessage("[debug] block: " + key + " count: " + cat.get(key));
			}
			Category defCat = ps.get("default");
			sender.sendMessage("[debug] total destroyed: " + defCat.get("totalblockdestroy"));
			sender.sendMessage("[debug] total placed: " + defCat.get("totalblockplace"));
			return true;
		}
		
		if (args[0].equalsIgnoreCase("kills")) {
			PlayerStat ps = SGStats.stats.get(sender.getName());
			if (ps.contains("kill")) {
				Category cat = ps.get("kill");
				for (String key : cat.getEntries()) {
					sender.sendMessage("[debug] type: " + key + " count: " + cat.get(key));
				}
			}
			if (ps.contains("default")) {
				Category defCat = ps.get("default");
				if (defCat.contains("totalkill"))
					sender.sendMessage("[debug] total kills: " + defCat.get("totalkill"));
				if (defCat.contains("totaldeath"))
					sender.sendMessage("[debug] total deaths: " + defCat.get("totaldeath"));
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("ach")) {
			for (String aName : SGStats.achievements.keySet()) {
				Achievement ach = SGStats.achievements.get(aName);
				sender.sendMessage("[debug] achieve: " + ach.getName() + " fn: " + ach.getFriendlyName() + " cat: " + ach.getCategory() + " k/v: " + ach.getStat() + "/" + ach.getValue());
			}
			return true;
		}
		return false;
	}

}
