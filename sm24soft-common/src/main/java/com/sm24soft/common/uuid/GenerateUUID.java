package com.sm24soft.common.uuid;

/**
 * Generate UUID implementation
 * 
 * @author sondn
 *
 */
public class GenerateUUID {
	
	/**
	 * Generate an unique ID without dashes
	 * 
	 * @return
	 */
	public static String randomUUID() {
		return new UUID().toString();
	}
	
}
