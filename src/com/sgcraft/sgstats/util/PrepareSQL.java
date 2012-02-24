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

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sgcraft.sgstats.SGStats;

public class PrepareSQL {
	public static SGStats plugin;
	private static Boolean useMysql;
	private static String sqliteDbName = "stats";
	private static String PREFIX = "SGStats";
	private static String DATABASE_PREFIX = null;
	private static File sqlFile;
	private static String hostname;
	private static String port = "3306";
	private static String mysqlDbName;
	private static String username;
	private static String password;
	private static Connection connection;
	
	public PrepareSQL (SGStats instance,Boolean mysql) {
		useMysql = mysql;
		plugin = instance;
		if (mysql == false) {
			File folder = new File(plugin.getDataFolder().getPath());
			if (sqliteDbName.contains("/") ||
					sqliteDbName.contains("\\") ||
					sqliteDbName.endsWith(".db")) {
				writeError("The database name can not contain: /, \\, or .db", true);
			}
			if (!folder.exists()) {
				folder.mkdir();
			}
			sqlFile = new File(folder.getAbsolutePath() + File.separator + sqliteDbName + ".db");
			DATABASE_PREFIX = "SQLite";
		} else {
			hostname = SGStats.config.getString("default.mysql-hostname");
			port = SGStats.config.getString("default.mysql-port");
			mysqlDbName = SGStats.config.getString("default.mysql-database");
			username = SGStats.config.getString("default.mysql-username");
			password = SGStats.config.getString("default.mysql-password");
			DATABASE_PREFIX = "MySQL";
		}
	}
	
	public Connection getConnection() {
		if (connection == null)
			return open();
		return connection;
	}
	
	protected boolean initialize() {
		if (useMysql) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				return true;
		    } catch (ClassNotFoundException e) {
		    	writeError("Class Not Found Exception: " + e.getMessage() + ".", true);
		    	return false;
		    }
		} else {
			try {
			  Class.forName("org.sqlite.JDBC");
			  
			  return true;
			} catch (ClassNotFoundException e) {
			  writeError("You need the SQLite library " + e, true);
			  return false;
			}
		}
	}
	
	public Connection open() {
		if (initialize()) {
			if (useMysql) {
				String url = "";
			    try {
					url = "jdbc:mysql://" + hostname + ":" + port + "/" + mysqlDbName;
					return DriverManager.getConnection(url, username, password);
			    } catch (SQLException e) {
			    	writeError(url,true);
			    	writeError("Could not be resolved because of an SQL Exception: " + e.getMessage() + ".", true);
			    }
			} else {
				try {
				  return DriverManager.getConnection("jdbc:sqlite:" +
						  	   sqlFile.getAbsolutePath());
				} catch (SQLException e) {
				  writeError("SQLite exception on initialize " + e, true);
				}
			}
		}
		return null;
	}
	
	public static Boolean isMysql() {
		return useMysql;
	}
	
	public final static String getPlayerStatUpdateStatement() {
		return "UPDATE player_stats SET value=? WHERE player = ? AND category = ? AND stat = ?;";
	}
	
	public final static String getPlayerStatInsertStatement() {
		return "INSERT INTO player_stats VALUES (?,?,?,?);";
	}
	
	public final static String getPlayerStat() {
		return "SELECT * FROM player_stats WHERE player=?;";
	}
	
	private static void writeError(String toWrite, boolean severe) {
		if (toWrite != null) {
			if (severe) {
				plugin.log.severe(PREFIX + DATABASE_PREFIX + toWrite);
			} else {
				plugin.log.warning(PREFIX + DATABASE_PREFIX + toWrite);
			}
		}
	}
}
