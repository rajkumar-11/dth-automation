package com.amdocs.weeklydump.fileops;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.amdocs.weeklydump.config.ConfigUtil;

public class FormatData {

	private String formatDate(String date) {
		String formatedDate = null;

		return formatedDate;
	}

	public static ArrayList<String> getDumpFormatedData(String userName, String Password, String tableName,
			ResultSet rs) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			int columnCount = rs.getMetaData().getColumnCount();
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i <= columnCount; i++) {
				if (i == columnCount) {
					builder.append(rs.getMetaData().getColumnName(i));
				} else {
					builder.append(rs.getMetaData().getColumnName(i) + "	");
				}
			}
			list.add(builder.toString() + System.lineSeparator());
			builder.setLength(0);
			int n = 0;
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if (i == columnCount) {
						if (rs.getString(i) != null) {
							builder.append(rs.getString(i));
						} else if (rs.getString(i) == null) {
							builder.append("	");
						}
					} else {
						if (rs.getMetaData().getColumnTypeName(i).equals("DATE") && rs.getString(i) != null) {
							String pattern = ConfigUtil.getValue("file.date.format");
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
							Date date = rs.getDate(i);
							String formatedDate = simpleDateFormat.format(date).toUpperCase();
							builder.append(formatedDate + "	");
						} else if (rs.getString(i) != null){
							builder.append(rs.getString(i) + "	");
						}else if (rs.getString(i) == null) {
							builder.append("	");
						}
					}
				}
				list.add(builder.toString() + System.lineSeparator());
				builder.setLength(0);
				n++;
			}

		} catch (

		Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
