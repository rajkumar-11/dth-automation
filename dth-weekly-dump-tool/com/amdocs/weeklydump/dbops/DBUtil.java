package com.amdocs.weeklydump.dbops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.amdocs.weeklydump.config.ConfigUtil;

public class DBUtil {

	private static Connection getConnection(String userName, String password) {
		try {
			String con = "jdbc:oracle:thin:@" + ConfigUtil.getValue("db.host") + ":" + ConfigUtil.getValue("db.port")
					+ "/" + ConfigUtil.getValue("db.sid");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(con, userName, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String fetchAll(String table) {
		return "SELECT * FROM" + " " +ConfigUtil.getValue("db.schema")+"."+ table;
	}

	public static ResultSet getWeeklyDump(String user, String password, String table) {

		try {
			Connection connection = getConnection(user, password);
			Statement statement = connection.createStatement();
			String qry = fetchAll(table);
			ResultSet resultSet = statement.executeQuery(qry);
			return resultSet;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}	
	public static String fetchById(String table, String remarks) {
		return "SELECT * FROM" + " " +ConfigUtil.getValue("db.schema")+"."+ table+" "+"WHERE REMARKS=" +remarks;
	}
}
