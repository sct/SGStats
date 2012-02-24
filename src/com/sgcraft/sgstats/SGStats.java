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
package com.sgcraft.sgstats;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sgcraft.sgstats.commands.StatCommands;
import com.sgcraft.sgstats.listeners.BlockListener;
import com.sgcraft.sgstats.listeners.EntityListener;
import com.sgcraft.sgstats.listeners.PlayerListener;
import com.sgcraft.sgstats.util.DeathDetail.DeathEventType;
import com.sgcraft.sgstats.util.PrepareSQL;
import com.sgcraft.sgstats.util.StatUtils;

public class SGStats extends JavaPlugin {
	public static SGStats plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public static HashMap<String,PlayerStat> stats = new HashMap<String,PlayerStat>();
	public static HashMap<String,Achievement> achievements = new HashMap<String,Achievement>();
	public static FileConfiguration config;
	public static FileConfiguration aConfig;
	static File aConfigFile = null;
	public static String defaultCategory = "default";
	public static Boolean debugMode = true;
	public static PrepareSQL sql;
	private static Integer interval = 300;
	
	public void configDatabase() {
		Connection conn = null;
		PreparedStatement ps = null;
		sql = new PrepareSQL(this,config.getBoolean("default.use-mysql"));
		conn = sql.getConnection();
		try {
			if (PrepareSQL.isMysql())
				ps = conn.prepareStatement("CREATE TABLE if not exists player_stats (player VARCHAR(255) NOT NULL,category VARCHAR(255) NOT NULL,stat VARCHAR(255) NOT NULL DEFAULT '-',value INT(11) NOT NULL DEFAULT 0);");
			else
				ps = conn.prepareStatement("CREATE TABLE if not exists player_stats (player TEXT NOT NULL,category TEXT NOT NULL,stat TEXT NOT NULL DEFAULT '-',value INTEGER NOT NULL DEFAULT 0);");
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addAchievements() {
		try {
			aConfigFile = new File(this.getDataFolder(),"achievements.yml");
			if (!aConfigFile.exists())
				aConfigFile.createNewFile();
			
			aConfig = YamlConfiguration.loadConfiguration(aConfigFile);
			InputStream defConfigStream = getResource("achievements.yml");
			if (defConfigStream != null) {
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				aConfig.setDefaults(defConfig);
			}
			
			aConfig.options().copyDefaults(true);
			aConfig.save(aConfigFile);
			
			ConfigurationSection aSec = aConfig.getConfigurationSection("achievements");
			Achievement achievement = null;
			for (String aName : aSec.getKeys(false)) {
				String aFriendly = aSec.getString(aName + ".friendly-name");
				String aDesc = aSec.getString(aName + ".description");
				String aCat = aSec.getString(aName + ".category");
				String aStat = aSec.getString(aName + ".stat");
				Integer aValue = aSec.getInt(aName + ".value");
				Boolean aHide = aSec.getBoolean(aName + ".hidden");
				String aMessage = aSec.getString(aName + ".message");
				if (aMessage.isEmpty())
					achievement = new Achievement(aName,aFriendly,aDesc,aCat,aStat,aValue,aHide);
				else
					achievement = new Achievement(aName,aFriendly,aDesc,aCat,aStat,aValue,aHide,aMessage);
				
				List<String> rewards = aConfig.getStringList(aName + ".rewards");
				
				for (String reward : rewards) {
					String[] args = reward.split(":");
					if (args[0] == "false")
						achievement.addReward(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
					else
						achievement.addReward(true, Integer.valueOf(args[2]));
				}
				achievements.put(aName, achievement);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdf = this.getDescription();
		saveTask(true);
		log.info("[" + pdf.getName() + "] is now disabled!");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdf = this.getDescription();
		config = getConfig();
        config.options().copyDefaults(true);
		saveConfig();
		configDatabase();
		startListeners();
		addCommands();
		addAchievements();
		startScheduler();
		log.info("[" + pdf.getName() + "] v" + pdf.getVersion() + " is now enabled!");
	}
	
	private void addCommands() {
		getCommand("stats").setExecutor(new StatCommands(this));
	}
	
	public void startListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
	}
	
	public void startScheduler() {
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				saveTask();
			}
		}, 300 * 20, interval * 20);
	}
	
	public void saveTask() {
		saveTask(false);
	}
	
	public void saveTask(Boolean unload) {
		Integer count = 0;
		if (debugMode == true)
			log.info("[SGStats] [DEBUG] Save task started...");
		for (String pName : stats.keySet()) {
			PlayerStat ps = stats.get(pName);
			if (unload)
				unload(ps.getPlayer());
			count++;
		}
		if (debugMode == true)
			log.info("[SGStats] [DEBUG] Save task finished. Saved " + count + " stats instances.");
	}
	
	public void updateStat(Player player,String stat) {
		updateStat(player,stat,1);
	}
	
	public void updateStat(Player player,String stat,Integer num) {
		updateStat(player.getName(),defaultCategory,stat,num);
	}
	
	public void updateStat(Player player,String stat,DeathEventType det) {
		updateStat(player,stat,det,1);
	}
	
	public void updateStat(Player player,String stat,DeathEventType det,Integer val) {
		updateStat(player.getName(),stat,det.toString(),val);
	}
	
	public void updateStat(Player player,String stat,Block block) {
		updateStat(player,stat,block,1);
	}
	
	public void updateStat(Player player,String stat,Block block,Integer num) {
		updateStat(player.getName(),stat,block.getType().toString(),1);
	}
	
	public void updateStat(String player,Achievement achieve) {
		updateStat(player,"achievements",achieve.getName(),1);
	}
	
	public void updateStat(String pName,String stat,String key,Integer value) {
		if (!stats.containsKey(pName))
			return;
		
		PlayerStat ps = stats.get(pName);
		Category cat = ps.get(stat);
		if (cat == null)
			cat = ps.newCategory(stat);
		cat.add(key, value);
		Integer newValue = cat.get(key);
		if (debugMode == true)
			log.info("[SGStats] [DEBUG] Stat Updated! Player: " + pName + " Cat: " + stat + " Key: " + key + " Value: " + value);
		
		// Get current achievements
		Category aCat = null;
		if (ps.contains("achievements"))
			aCat = ps.get("achievements");
		Achievement achievement = checkAchievements(aCat,stat,key,newValue);
		if (achievement != null)
			grantAchievement(ps,achievement);
	}
	
	public Achievement checkAchievements(Category cat,String cName, String key, Integer value) {
		Achievement achievement = null;
		for (String aName : achievements.keySet()) {
			achievement = achievements.get(aName);
			if (achievement.getCategory().equalsIgnoreCase(cName) && achievement.getStat().equalsIgnoreCase(key) && value >= achievement.getValue() 
					&& (cat == null || !cat.contains(achievement.getName()))) {
				if (debugMode == true)
					log.info("[SGStats] [Debug] Achievement detected! Name: " + achievement.getName());
				return achievement;
			}
		}
		
		return null;
	}
	
	public void grantAchievement(PlayerStat ps, Achievement ach) {
		String cMessage = config.getString("achievements.message");
		Boolean broadcast = config.getBoolean("achievements.broadcast");
		
		String newMessage = cMessage.replace("#name#", ach.getFriendlyName());
		if (ach.getMessage() != null)
			newMessage = newMessage.replace("#message#", ach.getMessage());
		else
			newMessage = newMessage.replace("#message#", "");
		
		ps.getPlayer().sendMessage(StatUtils.replaceColors(newMessage));
		
		if (broadcast == true) {
			String broadcastMessage = config.getString("achievements.broadcast-message");
			broadcastMessage = broadcastMessage.replace("#player#",ps.getPlayer().getName());
			broadcastMessage = broadcastMessage.replace("#name#", ach.getFriendlyName());
			getServer().broadcastMessage(StatUtils.replaceColors(broadcastMessage));
		}
		
		updateStat(ps.getPlayer().getName(),ach);
		ps.save();
	}
	
	
	public Integer get(String pName,String cName,String key) {
		if (!stats.containsKey(pName))
			return 0;
		
		PlayerStat ps = stats.get(pName);
		Category cat = ps.get(cName);
		if (cat == null)
			return 0;
		return cat.get(key);
	}
	
	public void load(Player player) {
		if (stats.containsKey(player.getName())) {
			LogError("Player already loaded");
			return;
		}
		PlayerStat ps = new PlayerStat(player);
		ps.load();
		stats.put(player.getName(), ps);
		if (debugMode)
			log.info("[SGStats] [DEBUG] Player Loaded: " + player.getName());
	}
	
	public void unload(Player player) {
		if (stats.containsKey(player.getName())) {
			PlayerStat ps = stats.get(player.getName());
			ps.save();
			stats.remove(player.getName());
		}
	}
	
	public void LogError(String error) {
		log.info("[SGStats] " + error);
	}

}
