/*
 *  SGStats - Elegant Stat Tracking and Achievements
 *  Copyright (C) 2012  SGCraft
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
