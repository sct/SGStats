package com.sgcraft.sgstats.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sgcraft.sgstats.SGStats;

public class PlayerListener implements Listener {
	public static SGStats plugin;
	
	public PlayerListener (SGStats instance) {
		plugin = instance;
	}
	
	@EventHandler()
	public void onPlayerJoin (PlayerJoinEvent event) {
		plugin.load(event.getPlayer());
	}
	
	@EventHandler()
	public void onPlayerQuit (PlayerQuitEvent event) {
		plugin.save(event.getPlayer());
	}
}
