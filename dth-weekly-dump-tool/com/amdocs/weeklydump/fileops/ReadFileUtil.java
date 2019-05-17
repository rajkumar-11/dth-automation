package com.amdocs.weeklydump.fileops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.amdocs.weeklydump.config.ConfigUtil;

public class ReadFileUtil {

	public static ArrayList<String> getTablesFromFile(String path) {

		FileReader fileReader;
		ArrayList<String> al = new ArrayList<String>();

		try {
			fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				al.add(line);
			}
			fileReader.close();
			bufferedReader.close();
			return al;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String moveFile(String source, String destination) {
		try {
			File file = new File(System.getProperty("user.dir") + "\\" + source);
			File[] files = file.listFiles();
			File file2 = new File(files[0].toString());
			if (file2.renameTo(
					new File(System.getProperty("user.dir") + "\\" + destination + "\\" + files[0].getName()))) {
				System.out.println("FILE_: " + file.getName() + "_MOVED_TO_" + destination);
				file2.delete();
				return destination;
			} else {
				System.out.println("DUMP_CAN_NOT_MOVE_TO_BACKUP");
				return null;
			}
		} catch (Exception e) {
			System.out.println("DUMP_NOT_CREATED_FILE_COUNT:"+e.getMessage());
			
			return null;
		}

	}
	
	public static void deleteExisting(String path) {
		File file= new File(path+"\\"+SFTPTempFile.getFileName(path));
		if(file.exists()) {
			file.delete();
		}
	}

	public static void deleteFiles(String path) {
		try {
			File file = new File(System.getProperty("user.dir") + "\\" + path);
			File[] files = file.listFiles();
			for(File f: files) {
				f.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static ArrayList<String> getAllTables() {
		String path = System.getProperty("user.dir") + "\\" + ConfigUtil.getValue("file.tables");
		ArrayList<String> al = getTablesFromFile(path);
		return al;
	}
}
