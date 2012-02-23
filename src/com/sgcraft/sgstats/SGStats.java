package com.sgcraft.sgstats;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.block.Block;
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
	public static String defaultCategory = "default";
	public static Boolean debugMode = true;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdf = this.getDescription();
		
		log.info("[" + pdf.getName() + "] is now disabled!");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdf = this.getDescription();
		
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
	}
	
	public Integer get(String pName,String stat,String key) {
		if (!stats.containsKey(pName))
			return 0;
		
		PlayerStat ps = stats.get(pName);
		Category cat = ps.get(stat);
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
		stats.put(player.getName(), ps);
		if (debugMode)
			log.info("[SGStats] [DEBUG] Player Loaded: " + player.getName());
	}
	
	public void LogError(String error) {
		log.info("[SGStats] " + error);
	}

}
