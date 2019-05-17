package com.amdocs.weeklydump.fileops;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.amdocs.weeklydump.config.ConfigUtil;

public class WriteFileUtill {

	public static void writeInFile(String path, String fileName, ArrayList<String> data) {
		File file;
		FileWriter fileWriter;
		try {
			file = new File(System.getProperty("user.dir") + "\\" + path + "\\" + fileName + ".txt");
			file.createNewFile();
			fileWriter = new FileWriter(file);
			if (!data.isEmpty()) {
				for (String s : data) {
					fileWriter.write(s);
				}
			}
			fileWriter.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeInFileAppend(String path, String fileName, ArrayList<String> data) {
		File file;
		FileWriter fileWriter;
		BufferedWriter bw = null;
		
		try {
			file = new File(System.getProperty("user.dir") + "\\" + path + "\\" + fileName + ".txt");
			file.createNewFile();
			fileWriter = new FileWriter(file.getAbsoluteFile(),true);
			bw=new BufferedWriter(fileWriter);
			if (!data.isEmpty()) {
				for (String s : data) {
					bw.write(s);
				}
			}
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeCSV(String data) {
		FileWriter fileWriter = null;
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("d-MM-YYYY");
		String zipName = dateFormatter.format(now);
		try {
			fileWriter = new FileWriter(ConfigUtil.getValue("file.log")+"\\LOG_"+zipName+".csv", true);
			fileWriter.append(data+"\n");
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
