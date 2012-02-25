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
package com.sgcraft.sgstats.util;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class StatUtils {
	
	public static EntityType getEntityType(Entity entity) {
		if (entity instanceof Creeper)
			return EntityType.CREEPER;
		if (entity instanceof Skeleton)
			return EntityType.SKELETON;
		if (entity instanceof Spider)
			return EntityType.SPIDER;
		if (entity instanceof Zombie)
			return EntityType.ZOMBIE;
		if (entity instanceof Giant)
			return EntityType.GIANT;
		if (entity instanceof Slime)
			return EntityType.SLIME;
		if (entity instanceof Ghast)
			return EntityType.GHAST;
		if (entity instanceof PigZombie)
			return EntityType.PIG_ZOMBIE;
		if (entity instanceof Enderman)
			return EntityType.ENDERMAN;
		if (entity instanceof CaveSpider)
			return EntityType.CAVE_SPIDER;
		if (entity instanceof Silverfish)
			return EntityType.SILVERFISH;
		if (entity instanceof Blaze)
			return EntityType.BLAZE;
		if (entity instanceof MagmaCube)
			return EntityType.MAGMA_CUBE;
		if (entity instanceof EnderDragon)
			return EntityType.ENDER_DRAGON;
		if (entity instanceof Pig)
			return EntityType.PIG;
		if (entity instanceof Sheep)
			return EntityType.SHEEP;
		if (entity instanceof Chicken)
			return EntityType.CHICKEN;
		if (entity instanceof Cow)
			return EntityType.COW;
		if (entity instanceof Squid)
			return EntityType.SQUID;
		if (entity instanceof Wolf)
			return EntityType.WOLF;
		if (entity instanceof MushroomCow)
			return EntityType.MUSHROOM_COW;
		if (entity instanceof Snowman)
			return EntityType.SNOWMAN;
		if (entity instanceof Villager)
			return EntityType.VILLAGER;
		
		return null;
	}
	
	public static String replaceColors(String replace) {
		return replace.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
	}
}
