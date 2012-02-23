package com.sgcraft.sgstats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.sgcraft.sgstats.SGStats;

public class BlockListener implements Listener {
	public static SGStats plugin;
	
	public BlockListener (SGStats instance) {
		plugin = instance;
	}
	
	@EventHandler()
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled())
			return;
		if (!(event.getPlayer() instanceof Player))
			return;
		plugin.updateStat(event.getPlayer(), "blockdestroy", event.getBlock());
		plugin.updateStat(event.getPlayer(), "totalblockdestroy");
	}
	
	@EventHandler()
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.isCancelled())
			return;
		if (!(event.getPlayer() instanceof Player))
			return;
		plugin.updateStat(event.getPlayer(), "blockplace", event.getBlock());
		plugin.updateStat(event.getPlayer(), "totalblockplace");
	}

}
