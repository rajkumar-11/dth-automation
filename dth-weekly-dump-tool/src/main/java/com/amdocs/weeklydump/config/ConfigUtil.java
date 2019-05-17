package com.amdocs.weeklydump.config;

import java.io.FileReader;
import java.util.Properties;

public class ConfigUtil {

	public static Properties getProps(String path) {
		FileReader reader;
		try {
			reader = new FileReader(path);
			Properties p = new Properties();
			p.load(reader);
			reader.close();
			return p;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String getValue(String key) {
		try {
			Properties p = getProps(System.getProperty("user.dir")+"\\config\\tool.properties");
			return p.getProperty(key);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
