package com.sm24soft.common.util;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class TokenUtil {

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890!@#$";

	public static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}
	
	/**
	 * This will generate random ID up to 8 characters long
	 * 
	 * @return
	 */
	public static String getToken() {
		char[] chars = CHARS.toCharArray();
		Random r = new Random(System.currentTimeMillis());
		char[] id = new char[8];
		for (int i = 0; i < 8; i++) {
			id[i] = chars[r.nextInt(chars.length)];
		}
		return new String(id);
	}
	
	/**
	 * Generate short UUID (13 characters). NOTE: It is only less unique the
	 * UUID because the 128 bit
	 * 
	 * @return short UUID
	 */
	public static String getToken2() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}
	
}
