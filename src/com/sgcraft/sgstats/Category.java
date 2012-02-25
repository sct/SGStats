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
	
	public Boolean contains(String stat) {
		if (stats.containsKey(stat))
			return true;
		else
			return false;
	}
	
	public Boolean isModified() {
		return this.modified;
	}
	
	public int get(String stat) {
		int value;
		if (stats.containsKey(stat))
			value = stats.get(stat);
		else
			value = 0;
		return value;
	}
	
	public void put(String stat, Integer value) {
		stats.put(stat, value);
		modified = true;
	}
	
	public void set(String stat, Integer value) {
		put(stat, value);
	}
	
	public void setModified(Boolean modified) {
		this.modified = modified;
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
