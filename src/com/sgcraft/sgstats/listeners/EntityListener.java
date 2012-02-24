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
import org.bukkit.event.entity.EntityDeathEvent;

import com.sgcraft.sgstats.SGStats;
import com.sgcraft.sgstats.util.DeathDetail;
import com.sgcraft.sgstats.util.DeathDetail.DeathEventType;

public class EntityListener implements Listener {
	public static SGStats plugin;
	
	public EntityListener (SGStats instance) {
		plugin = instance;
	}
	
	@EventHandler()
	public void onEntityDeath (EntityDeathEvent event) {
		DeathDetail dd = new DeathDetail(event);
		if (dd.getDeathType() != null && dd.getDeathType().equals(DeathEventType.PVP)) {
			Player player = dd.getPlayer();
			Player killer = dd.getKiller();
			plugin.updateStat(player, "totaldeath");
			plugin.updateStat(killer, "kill",dd.getDeathType());
			return;
		}
		if (dd.getPlayer() != null) {
			plugin.updateStat(dd.getPlayer(), "totaldeath");
			return;
		}
		
		if (dd.isCreature()) {
			Player killer = dd.getKiller();
			plugin.updateStat(killer,"kill",dd.getDeathType());
			plugin.updateStat(killer,"totalkill");
		}
		
	}

}
