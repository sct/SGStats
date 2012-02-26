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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.sgcraft.sgstats.Achievement;
import com.sgcraft.sgstats.Category;
import com.sgcraft.sgstats.PlayerStat;
import com.sgcraft.sgstats.SGStats;

public class AchvCommands implements CommandExecutor {
	public static SGStats plugin;
	public static String cmdName = null;
	public static String cmdDesc = null;
	public static String cmdUsage = null;
	public static String pluginName;
	public static String pluginVersion;
	
	public AchvCommands (SGStats instance) {
		plugin = instance;
		PluginDescriptionFile pdf = plugin.getDescription();
		pluginName = pdf.getName();
		pluginVersion = pdf.getVersion();
	}
	
	private boolean checkPerm(Player player, String perm) {
		if (player.isOp() || player.hasPermission(pluginName.toLowerCase() + "." + perm.toLowerCase()))
			return true;
		else {
			sendErr(player,"You do not have permission.");
			return false;
		}
	}
	
	
	private boolean checkPerm(Player player, String perm, Boolean noerror) {
		if (player.isOp() || player.hasPermission(pluginName.toLowerCase() + "." + perm.toLowerCase()))
			return true;
		else {
			return false;
		}
	}
	
	
	private void sendErr(Player player, String msg) {
		msg = "§c" + msg;
		sendMsg(player,msg);
	}
	
	private void sendMsg(Player player, String msg) {
		player.sendMessage("§5[§6" + pluginName + "§5] §f" + msg);
	}
	
	private boolean statCommand(String label,String[] args, CommandSender sender) {
		if (args[0].equalsIgnoreCase(label) && checkPerm((Player) sender, label))
			return true;
		else
			return false;
	}
	
	/*
	private boolean statCommand(String label,String[] args, CommandSender sender, String perm) {
		if (args[0].equalsIgnoreCase(label) && checkPerm((Player) sender, perm))
			return true;
		else
			return false;
	}
	*/
	
	private void displayCmdHelp(Player player) {
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
		player.sendMessage("§f| §bCommand: §3" + cmdName);
		player.sendMessage("§f| §bDescription: §3" + cmdDesc);
		player.sendMessage("§f| §bUsage: §3" + cmdUsage);
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
	}
	
	private void displayHelp(Player player) {
		player.sendMessage("§5[§6 Achievement Help §5]§f--------------------------");
		player.sendMessage("§f| §b/achv list §3[user]");
		player.sendMessage("§f| §b/achv listall §3<title>");
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
	}
	
	private void formatAchievements(Player player,Integer page) {
		formatAchievements(player,player,page,false);
	}
	
	private void formatAchievements(CommandSender sender,Player player,Integer page, Boolean isPlayer) {
		List<Achievement> aList = null;
		Integer currentIndex = (page * 5) - 5;
		Integer x = 0;
		PlayerStat ps = SGStats.stats.get(player.getName());
		Category cat = ps.get("achievements");
		if (isPlayer != true) {
			aList = new ArrayList<Achievement>(SGStats.achievements.values());
		} else {
			aList = new ArrayList<Achievement>();
			if (cat == null) {
				sender.sendMessage("  §3No achievements");
				return;
			}
			for (String aName : cat.getEntries()) {
				aList.add(SGStats.achievements.get(aName));
			}
		}
		Integer totalPage = (int) Math.ceil((float) aList.size() / 5);
		for (x = currentIndex;x <= (currentIndex + 4);x++) {
			try {
				Achievement achv = aList.get(x);
				String achName = achv.getFriendlyName();
				String achDesc = achv.getDescription();
				if (achv.isHidden() && isPlayer == false && !checkPerm(player,"admin.hidden",true)) {
					achName = "<Hidden>";
					achDesc = "???";
				}
				if (cat != null && cat.contains(achv.getName()))
					sender.sendMessage("  §3" + achName + " - §7" + achDesc);
				else
					sender.sendMessage("  §8" + achName + " - §7" + achDesc);
			} catch (IndexOutOfBoundsException e) {
				// Skip missing rows
			}
		}
		sender.sendMessage("§5[§6 Page §7(" + page + "/" + totalPage + ") §5] [§6 Total: §3" + aList.size() + " §5]");
	} 

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (sender instanceof ConsoleCommandSender)
        {
			sender.sendMessage("Sorry, you can not run these commands from the console!");
            return true;
        }
		
        if (args.length == 0 || args[0].equals("?")) {
        	displayHelp((Player) sender);
        	return true;
        }
        
        if (statCommand("list",args,sender)) {
        	cmdName = "List";
        	cmdDesc = "List achievements earned";
        	cmdUsage = "/achv list [user] <page>";
        	if (args.length > 1 && args[1].equalsIgnoreCase("?")) {
        		displayCmdHelp((Player) sender);
        		return true;
        	}
        	if (args.length > 1) {
        		Player target = Bukkit.getServer().getPlayer(args[1]);
        		if (target != null) {
        			if (args.length == 3) {
        				try {
            				Integer page = Integer.parseInt(args[2]);
            				sender.sendMessage("§5[§6 " + target.getName() + "'s Achievements §5]§f");
            				formatAchievements(sender,target,page,true);
            			} catch (NumberFormatException e) {
            				sendErr((Player) sender,"Page must be a number.");
            			}
        			} else {
        				sender.sendMessage("§5[§6 " + target.getName() + "'s Achievements §5]§f");
        				formatAchievements(sender,target,1,true);
        			}
        		} else {
        			try {
        				Integer page = Integer.parseInt(args[1]);
        				sender.sendMessage("§5[§6 Your Achievements §5]§f");
        				formatAchievements(sender,(Player) sender,page,true);
        			} catch (NumberFormatException e) {
        				sendErr((Player) sender,"Page must be a number.");
        			}
        		}
        	} else {
        		sender.sendMessage("§5[§6 Your Achievements §5]§f");
        		formatAchievements(sender,(Player) sender,1,true);
        	}
        	return true;
        }
        
        if (statCommand("listall",args,sender)) {
        	cmdName = "List All";
        	cmdDesc = "Lists all available achievements";
        	cmdUsage = "/achv listall <type>";
        	if (args.length > 1 && args[1].equalsIgnoreCase("?")) {
        		displayCmdHelp((Player) sender);
        		return true;
        	}
        	
        	Integer page = 1;
    		if  (args.length == 2) {
    			try {
    				page = Integer.parseInt(args[1]);
    			} catch (NumberFormatException e) {
    				sendErr((Player) sender,"Page must be a number.");
    			}
    		}
    		
    		sender.sendMessage("§5[§6 Achievements §5]§f-------------------------------");
    		formatAchievements((Player) sender,page);
        	
        	return true;
        }
		/*
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
		*/
		return true;
	}

}
