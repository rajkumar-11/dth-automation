package com.amdocs.weeklydump.validation;

import java.io.BufferedReader;
import java.io.File;

import com.amdocs.weeklydump.config.ConfigUtil;
import com.amdocs.weeklydump.fileops.WriteFileUtill;

public class ValidateDumpUtil {

	public static int countLines(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new java.io.FileReader(new File(
					System.getProperty("user.dir") + "\\" + ConfigUtil.getValue("file.dump") + "\\" + fileName)));
			int lines = 0;
			while (reader.readLine() != null) {
				lines++;
			}
			reader.close();
			return lines;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	public static void validateDump(String table, int count) {
		if (count == countLines(table + ".txt")) {
			WriteFileUtill.writeCSV(table + "," + "Valid");
		} else {
			WriteFileUtill.writeCSV(table + "," + "Invalid");
		}
	}
}
