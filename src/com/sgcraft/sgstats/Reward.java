package com.sgcraft.sgstats;

public class Reward {
	private Boolean isEconomy;
	private Integer itemId;
	private Integer value;
	
	public Reward (Boolean isEconomy, Integer itemId, Integer value) {
		this.isEconomy = isEconomy;
		this.itemId = itemId;
		this.value = value;
	}
	
	public Boolean isEconomy() {
		return this.isEconomy;
	}
	
	public Integer getItemId() {
		return this.itemId;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public void setEconomy(Boolean economy) {
		this.isEconomy = economy;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
}
