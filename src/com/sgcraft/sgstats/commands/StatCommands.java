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
		return false;
	}

}
