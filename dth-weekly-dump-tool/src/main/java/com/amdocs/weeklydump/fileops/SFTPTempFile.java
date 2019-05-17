package com.amdocs.weeklydump.fileops;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amdocs.weeklydump.config.ConfigUtil;

public class SFTPTempFile {

	public static String getFileName(String path) {
		String filename = null;
		File file = new File(ConfigUtil.getValue("file.backup"));
		File[] files = file.listFiles();
		String pattern = "dd-MM-yyy";
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String formatedDate = dateFormat.format(date);
		for (File file2 : files) {
			if (file2.getName().contains(formatedDate)) {
				filename = file2.getName();
			}
		}
		return filename;
	}

	public static String getRoot() {
		String root = System.getProperty("user.dir");
		return root;
	}

	public static String getConUrl() {
		String sftpurl = "open sftp://" + ConfigUtil.getValue("ftp.user") + ":" + ConfigUtil.getValue("ftp.pass") + "@"
				+ ConfigUtil.getValue("ftp.host") + "/ -hostkey=\"" + ConfigUtil.getValue("ftp.key") + "\"";
		return sftpurl;
	}

	public static String getFtpPath() {
		String sftpurl = "put " + System.getProperty("user.dir") +"\\"+ ConfigUtil.getValue("file.backup") + "\\"
				+ getFileName(ConfigUtil.getValue("file.backup")) + "\t" + ConfigUtil.getValue("ftp.remoteuri");
		return sftpurl;
	}

	public static void createTempFTPConfig() {
		String rootPath = getRoot();
		try {
			File e = new File(System.getProperty("user.dir")  + "\\FTPSetup\\FTP\\FTPConfig.txt");
			e.createNewFile();
			PrintWriter writer = new PrintWriter(rootPath + "\\FTPSetup\\FTP\\FTPConfig.txt", "UTF-8");
			writer.println(getConUrl());
			writer.println(getFtpPath());
			writer.println("exit");
			writer.close();
		} catch (Exception arg3) {
			arg3.printStackTrace();
		}
	}
}
