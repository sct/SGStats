package com.sgcraft.sgstats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
		if (args[0].equalsIgnoreCase("blockstats")) {
			PlayerStat ps = SGStats.stats.get(sender.getName());
			plugin.log.info("[SGStats] [Debug] Got playerstat for player: " + ps.player.getName());
			Category cat = ps.get("blockdestroy");
			for (String key : cat.getEntries()) {
				sender.sendMessage("[debug] block: " + key + " count: " + cat.get(key));
			}
			Category defCat = ps.get("default");
			sender.sendMessage("[debug] total destroyed: " + defCat.get("totalblockdestroy"));
			return true;
		}
		
		if (args[0].equalsIgnoreCase("killstats")) {
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
		return false;
	}

}
