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

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sgcraft.sgstats.SGStats;

public class PlayerListener implements Listener {
	public static SGStats plugin;
	public static HashMap<String,Float> travel = new HashMap<String,Float>();
	public static HashMap<String,Integer> playertime = new HashMap<String,Integer>();
	
	public PlayerListener (SGStats instance) {
		plugin = instance;
	}
	
	@EventHandler()
	public void onPlayerJoin (PlayerJoinEvent event) {
		plugin.load(event.getPlayer());
		playertime.put(event.getPlayer().getName(), (int) (System.currentTimeMillis() / 1000L));
	}
	
	@EventHandler()
	public void onPlayerQuit (PlayerQuitEvent event) {
		plugin.unload(event.getPlayer());
		playertime.remove(event.getPlayer().getName());
	}
	
	@EventHandler()
	public void onPlayerMove (PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		
		if (travel.containsKey(event.getPlayer().getName())) {
			float distance = (travel.get(event.getPlayer().getName()) + (float) (event.getFrom().toVector().distance(event.getTo().toVector())));
			travel.put(event.getPlayer().getName(), distance);
		} else {
			travel.put(event.getPlayer().getName(), (float) event.getFrom().toVector().distance(event.getTo().toVector()));
		}
	}
}
