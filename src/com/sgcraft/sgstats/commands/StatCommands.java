package com.sgcraft.sgstats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import com.sgcraft.sgstats.Category;
import com.sgcraft.sgstats.PlayerStat;
import com.sgcraft.sgstats.SGStats;

public class StatCommands implements CommandExecutor {

	public static SGStats plugin;
	public static String cmdName = null;
	public static String cmdDesc = null;
	public static String cmdUsage = null;
	public static String pluginName;
	public static String pluginVersion;
	
	public StatCommands (SGStats instance) {
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
	
	/*
	private boolean checkPerm(Player player, String perm, Boolean noerror) {
		if (player.isOp() || player.hasPermission(pluginName.toLowerCase() + "." + perm.toLowerCase()))
			return true;
		else {
			return false;
		}
	}
	*/
	
	private void sendErr(Player player, String msg) {
		msg = "§c" + msg;
		sendMsg(player,msg);
	}
	
	private void sendMsg(Player player, String msg) {
		player.sendMessage("§5[§6" + pluginName + "§5] §f" + msg);
	}
	
	/*
	private boolean statCommand(String label,String[] args, CommandSender sender) {
		if (args[0].equalsIgnoreCase(label) && checkPerm((Player) sender, label))
			return true;
		else
			return false;
	}
	*/
	

	private boolean statCommand(String label,String[] args, CommandSender sender, String perm) {
		if (args[0].equalsIgnoreCase(label) && checkPerm((Player) sender, perm))
			return true;
		else
			return false;
	}
	
	private void displayCmdHelp(Player player) {
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
		player.sendMessage("§f| §bCommand: §3" + cmdName);
		player.sendMessage("§f| §bDescription: §3" + cmdDesc);
		player.sendMessage("§f| §bUsage: §3" + cmdUsage);
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
	}
	
	private void displayHelp(Player player) {
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
		player.sendMessage("§f| §b/stat");
		player.sendMessage("§5[§6 " + pluginName + " Help §5]§f--------------------------");
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof ConsoleCommandSender)
        {
			sender.sendMessage("Sorry, you can not run these commands from the console!");
            return true;
        }
		
        if (args.length == 1 && args[0].equals("?")) {
        	displayHelp((Player) sender);
        	return true;
        }
        
        if (args.length == 0) {
        	PlayerStat ps = SGStats.stats.get(sender.getName());
        	sender.sendMessage("§5[§6 Your Statistics §5]§f");
        	if (ps.contains("default")) {
        		Category dCat = ps.get("default");
        		sender.sendMessage("  §3Total Achievements: §7" + dCat.get("totalachievements"));
        		sender.sendMessage("  §3Total Blocks Broken: §7" + dCat.get("totalblockdestroy"));
        		sender.sendMessage("  §3Total Blocks Placed: §7" + dCat.get("totalblockplace"));
        		sender.sendMessage("  §3Total Monsters Killed: §7" + dCat.get("totalkill"));
        		sender.sendMessage("  §3Total Deaths: §7" + dCat.get("totaldeath"));
        	}
        	
        	return true;
        }
        
        if (statCommand("reload",args,sender,"admin.reload")) {
        	cmdName = "Reload";
        	cmdDesc = "Reloads config.yml";
        	cmdUsage = "/stat reload";
        	if (args.length > 1 && args[1].equalsIgnoreCase("?")) {
        		displayCmdHelp((Player) sender);
        		return true;
        	}
        	
        	plugin.reload();
        	sendMsg((Player) sender,"Config reloaded!");
        	
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
