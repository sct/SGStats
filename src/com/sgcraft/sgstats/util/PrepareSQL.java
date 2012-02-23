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
