package com.sm24soft.util;

import java.io.File;

public final class FileUtil extends com.sm24soft.common.util.FileUtil {
	
	public static final String DEFAULT_HOME_DIRECTORY = "/wp-content/uploads/";
	
	public static String getResourceDirectory(String resourceDir) {
		return createResourceDirectory(resourceDir, DEFAULT_HOME_DIRECTORY).getPath();
	}
	
	/**
	 * Create resource directory
	 * 
	 * @param resourceDir
	 * @param path
	 */
	public static File createResourceDirectory(String resourceDir, String path) {
		File dir = new File(resourceDir, path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
}
