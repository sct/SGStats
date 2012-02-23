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
