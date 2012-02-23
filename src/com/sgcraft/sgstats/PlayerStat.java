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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;

import com.sgcraft.sgstats.util.PrepareSQL;


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
	
	public Boolean contains(String name) {
		if (categories.containsKey(name))
			return true;
		else
			return false;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Category get(String name) {
		return categories.get(name);
	}
	
	public void save() {
		Connection conn = null;
		PreparedStatement ps = null;
		for (String cName : categories.keySet()) {
			Category cat = categories.get(cName);
			if (cat.isModified()) {
				for (String stat : cat.getEntries()) {
					try {
						conn = SGStats.sql.getConnection();
						ps = conn.prepareStatement(PrepareSQL.getPlayerStatUpdateStatement());
						ps.setInt(1, cat.get(stat));
						ps.setString(2, player.getName());
						ps.setString(3, cName);
						ps.setString(4, stat);
						if (ps.executeUpdate() == 0) {
							ps = conn.prepareStatement(PrepareSQL.getPlayerStatInsertStatement());
							ps.setString(1, player.getName());
							ps.setString(2, cName);
							ps.setString(3, stat);
							ps.setInt(4, cat.get(stat));
							ps.executeUpdate();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							if (conn != null)
								conn.close();
							if (ps != null)
								ps.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				cat.setModified(false);
			}
		}
	}
	
	public void load() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = SGStats.sql.getConnection();
			ps = conn.prepareStatement(PrepareSQL.getPlayerStat());
			ps.setString(1, player.getName());
			rs = ps.executeQuery();
			while (rs.next()) {
				put(rs.getString("category"),rs.getString("stat"),rs.getInt("value"));
			}
		} catch (SQLException e) {
			
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
