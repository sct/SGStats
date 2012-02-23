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

import java.util.ArrayList;
import java.util.List;

public class Achievement {
	private String name = null;
	private String friendlyName = null;
	private String category = null;
	private String stat = null;
	private Integer value = 0;
	private String message = null;
	private List<Reward> rewards = new ArrayList<Reward>();
	
	public Achievement (String name, String friendlyName, String category, String stat, Integer value, String message) {
		this.name = name;
		this.friendlyName = friendlyName;
		this.category = category;
		this.stat = stat;
		this.value = value;
		this.message = message;
	}
	
	public Achievement (String name, String friendlyName, String category, String stat, Integer value) {
		this.name = name;
		this.friendlyName = friendlyName;
		this.category = category;
		this.stat = stat;
		this.value = value;
	}
	
	public List<Reward> getRewards() {
		return this.rewards;
	}
	
	public void addReward(Boolean economy, Integer value) {
		if (economy)
			rewards.add(new Reward(economy, 0, value));
		return;
	}
	
	public void addReward(Integer itemId, Integer value) {
		rewards.add(new Reward(false,itemId,value));
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFriendlyName() {
		return this.friendlyName;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getStat() {
		return this.stat;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setStat(String stat) {
		this.stat = stat;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
