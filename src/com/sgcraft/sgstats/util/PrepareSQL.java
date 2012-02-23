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

public class PrepareSQL {
	
	public final static String getPlayerStatUpdateStatement() {
		return "UPDATE player_stats SET value=? WHERE player = ? AND category = ? AND stat = ?;";
	}
	
	public final static String getPlayerStatInsertStatement() {
		return "INSERT INTO player_stats VALUES (?,?,?,?);";
	}
	
	public final static String getPlayerStat() {
		return "SELECT * FROM player_stats WHERE player=?;";
	}
}
