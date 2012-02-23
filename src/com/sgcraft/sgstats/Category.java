package com.sgcraft.sgstats;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Category {
	public Boolean modified = false;
	public Map<String,Integer> stats = new HashMap<String,Integer>();
	
	public Set<String> getEntries() {
		return stats.keySet();
	}
	
	public int get(String stat) {
		int value = stats.get(stat);
		return value;
	}
	
	public void put(String stat, Integer value) {
		stats.put(stat, value);
		modified = true;
	}
	
	public void set(String stat, Integer value) {
		put(stat, value);
	}
	
	public void add(String stat, Integer value) {
		if (!stats.containsKey(stat)) {
			put(stat,value);
			return;
		}
		Integer oldVal = get(stat);
		put(stat, oldVal + value);
	}
	
	Iterator<String> iterator() {
		return stats.keySet().iterator();
	}

}
