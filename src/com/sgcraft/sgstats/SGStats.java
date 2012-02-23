package com.sgcraft.sgstats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.sgcraft.sgstats.commands.StatCommands;
import com.sgcraft.sgstats.listeners.BlockListener;
import com.sgcraft.sgstats.listeners.EntityListener;
import com.sgcraft.sgstats.listeners.PlayerListener;
import com.sgcraft.sgstats.util.DeathDetail.DeathEventType;

public class SGStats extends JavaPlugin {
	public static SGStats plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public static HashMap<String,PlayerStat> stats = new HashMap<String,PlayerStat>();
	public static HashMap<String,Achievement> achievements = new HashMap<String,Achievement>();
	public static FileConfiguration config;
	public static String defaultCategory = "default";
	public static Boolean debugMode = true;
	public static SQLite sql;
	
	public void configDatabase() {
		PluginDescriptionFile pdf = this.getDescription();
		Connection conn = null;
		PreparedStatement ps = null;
		if (!config.getBoolean("default.use-mysql")) {
			sql = new SQLite(log, "[ " + pdf.getName() + "]", "titles", getDataFolder().getPath());
			conn = sql.getConnection();
			try {
				ps = conn.prepareStatement("CREATE TABLE if not exists player_stats (player TEXT NOT NULL,category TEXT NOT NULL,stat TEXT NOT NULL DEFAULT '-',value INTEGER NOT NULL DEFAULT 0);");
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdf = this.getDescription();
		
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
	
	public void updateStat(String pName,String stat,String key,Integer value) {
		if (!stats.containsKey(pName))
			return;
		
		PlayerStat ps = stats.get(pName);
		Category cat = ps.get(stat);
		if (cat == null)
			cat = ps.newCategory(stat);
		cat.add(key, value);
		if (debugMode)
			log.info("[SGStats] [DEBUG] Stat Updated! Player: " + pName + " Cat: " + stat + " Key: " + key + " Value: " + value);
		
		Achievement achievement = null;
		
		for (String aName : achievements.keySet()) {
			achievement = achievements.get(aName);
			if (achievement.getCategory().equalsIgnoreCase(stat) && achievement.getStat().equalsIgnoreCase(key) && achievement.getValue() >= value) {
				// Give out achievement
			}
		}
	}
	/*
	public Achievement checkAchievements(String cName, String key, Integer value) {
		
	}
	*/
	
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
	
	public void save(Player player) {
		PlayerStat ps = stats.get(player.getName());
		ps.save();
		stats.remove(player.getName());
	}
	
	public void LogError(String error) {
		log.info("[SGStats] " + error);
	}

}
