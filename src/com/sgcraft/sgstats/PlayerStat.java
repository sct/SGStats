package com.sgcraft.sgstats;

import java.util.HashMap;

import org.bukkit.entity.Player;


public class PlayerStat {
	public Player player;
	public HashMap<String,Category> categories;
	
	public PlayerStat(Player player) {
		this.player = player;
		this.categories = new HashMap<String,Category>();
	}
	
	public Category newCategory(String name) {
		Category category = new Category();
		categories.put(name, category);
		return category;
	}
	
	public void put(String name, String stat, Integer value) {
		Category cat;
		if (!categories.containsKey(name))
			cat = newCategory(name);
		else
			cat = categories.get(name);
		cat.put(stat, value);
	}
	
	public Category get(String name) {
		return categories.get(name);
	}
	
	public void save() {
		// Stuff
	}
	
	public void load() {
		// Stuff
	}
}
