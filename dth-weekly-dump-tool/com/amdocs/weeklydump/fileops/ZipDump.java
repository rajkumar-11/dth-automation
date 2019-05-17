package com.amdocs.weeklydump.fileops;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.amdocs.weeklydump.config.ConfigUtil;

public class ZipDump {

	public static void zipFiles(String inputFilePath, String outputpath) {
		try {
			File path = new File(inputFilePath);
			String[] files = path.list();

			File outf = new File(outputpath);
			outf.createNewFile();

			FileOutputStream fos = new FileOutputStream(outf);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String file : files) {
				zos.putNextEntry(new ZipEntry(file));
				byte[] bytes = Files.readAllBytes(Paths.get(inputFilePath + "\\" + file));
				zos.write(bytes, 0, bytes.length);
			}
			zos.closeEntry();
			zos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void zipWeaklyDump() {
		Date now = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("d-MM-YYYY");
		String zipName = dateFormatter.format(now);
		String zipPath = System.getProperty("user.dir") + "\\" + ConfigUtil.getValue("file.temp") + "\\"+"WEEKLY_DUMP_"+ zipName + ".zip";
		String files=System.getProperty("user.dir")+"\\"+ConfigUtil.getValue("file.dump") + "\\";
		ZipDump.zipFiles(files, zipPath);
	}
}
