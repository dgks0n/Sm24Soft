package com.sm24soft.common.util;

import java.io.File;
import java.io.IOException;

/**
 * This class is utility used to delete a file or directly
 * 
 * @author sondn
 *
 */
public class FileUtil {
	
	public static final String DEFAULT_GALA_HOME_DIRECTORY = "Gala/Resources";

	/**
	 * Delete a file or directory
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				}
			}

		} else {
			// if file, then delete it
			file.delete();
		}
	}
	
	/**
	 * Get home directory
	 * 
	 * @return
	 */
	public static String getHomeDirectory(String dirName) {
		return getRootDirectory() + File.separator + dirName;
	}
	
	public static String getHomeDirectory() {
		return getRootDirectory() + File.separator + DEFAULT_GALA_HOME_DIRECTORY;
	}
	
	private static String getRootDirectory() {
		return System.getProperty("user.home");
	}
}
