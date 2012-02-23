package com.sgcraft.sgstats.util;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathDetail {
	private Player player = null;
	private Player killer = null;
	private DeathEventType deathType = null;
	private Boolean isCreature = false;
	
	public DeathDetail (EntityDeathEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			setPlayer((Player) e);
			
			EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();
			if (damageEvent instanceof EntityDamageByEntityEvent) {
				Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
				if (damager instanceof Player) {
					setKiller((Player) damager);
					setDamageType(DeathEventType.PVP);
				}
			}
		} else if (e instanceof Creature) {
			EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();
			if (damageEvent instanceof EntityDamageByEntityEvent) {
				Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
				if (damager instanceof Player) {
					isCreature = true;
					setKiller((Player) damager);
					try {
						deathType = DeathEventType.valueOf(StatUtils.getCreatureType(e).toString());
					} catch (IllegalArgumentException ex) {
						deathType = DeathEventType.UNKNOWN;
					}
				}
			}
		}
		
	}
	
	private void setDamageType(DeathEventType death) {
		this.deathType = death;
	}
	
	private void setPlayer(Player player) {
		this.player = player;
	}
	
	private void setKiller(Player player) {
		this.killer = player;
	}
	
	public DeathEventType getDeathType() {
		return this.deathType;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Player getKiller() {
		return this.killer;
	}
	
	public Boolean isCreature() {
		return this.isCreature;
	}
	
	public static enum DeathEventType {
        PVP, CREEPER, SKELETON, SPIDER, ZOMBIE, GIANT, SLIME, GHAST, PIG_ZOMBIE, ENDERMAN, ENDER_DRAGON, CAVE_SPIDER, SILVERFISH, BLAZE, MAGMA_CUBE,
        PIG, SHEEP, CHICKEN, COW, SQUID, WOLF, MUSHROOM_COW, SNOWMAN, VILLAGER, UNKNOWN
    };

}
