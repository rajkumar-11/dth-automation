package com.amdocs.weeklydump;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.amdocs.weeklydump.config.ConfigUtil;
import com.amdocs.weeklydump.dbops.DBUtil;
import com.amdocs.weeklydump.fileops.FormatData;
import com.amdocs.weeklydump.fileops.ReadFileUtil;
import com.amdocs.weeklydump.fileops.SFTPTempFile;
import com.amdocs.weeklydump.fileops.WriteFileUtill;
import com.amdocs.weeklydump.fileops.ZipDump;
import com.amdocs.weeklydump.validation.ValidateDumpUtil;

public class DumpBussiness {

	public static void weaklyDump(String userName, String password) {

		ArrayList<String> list = ReadFileUtil.getAllTables();
		ArrayList<String> data = null;
		String header="TABLENAME,STATUS";
		WriteFileUtill.writeCSV(header);
		for (String table : list) {
			System.out.println("PREPARING_DUMP_FOR_" + table + "_STARTED");
			ResultSet rs = DBUtil.getWeeklyDump(userName, password, table);
			data = FormatData.getDumpFormatedData(userName, password, table, rs);
			WriteFileUtill.writeInFile(ConfigUtil.getValue("file.dump"), table, data);
			System.out.println("DUMP_FILE_" + table + "_CREATED");
			ValidateDumpUtil.validateDump(table, data.size());
		}

		System.out.println("CREATING_ZIP");
		ZipDump.zipWeaklyDump();
		System.out.println("MOVING_DUMP_TO_BACKUP");
		ReadFileUtil.deleteExisting(ConfigUtil.getValue("file.backup"));
		ReadFileUtil.moveFile(ConfigUtil.getValue("file.temp"), ConfigUtil.getValue("file.backup"));
		System.out.println("FTP_STARTED");
		SFTPTempFile.createTempFTPConfig();
		System.out.println("CONGRATULATIONS_WEEKLY_DUMP_SEND");
		ReadFileUtil.deleteFiles(ConfigUtil.getValue("file.dump"));
		System.out.println("CLEANUP_DONE");
	}
}
