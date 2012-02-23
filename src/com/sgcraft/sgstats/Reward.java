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
