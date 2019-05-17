package com.amdocs.weeklydump;

import com.amdocs.weeklydump.config.ConfigUtil;

public class RunTool {
	public static void main(String[] args) {
		DumpBussiness.weaklyDump(ConfigUtil.getValue("db.user"), ConfigUtil.getValue("db.pass"));
	}
}
