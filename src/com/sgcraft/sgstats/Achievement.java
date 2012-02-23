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
