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
			plugin.getServer().broadcastMessage(player.getName() + " was killed by " + killer.getName());
			return;
		}
		if (dd.getPlayer() != null) {
			plugin.getServer().broadcastMessage("aw shucks, a player died!");
			return;
		}
		
		if (dd.isCreature()) {
			Player killer = dd.getKiller();
			plugin.getServer().broadcastMessage(killer.getName() + " just killed a " + dd.getDeathType().toString());
		}
		
	}

}
