package com.sm24soft.common.uuid;

import java.util.Random;

/**
 * Utility class with a "main" method that allows an application to run the
 * template generation
 * 
 * @author sondn
 *
 */
public class PasswordGenerator {

	/** ALPHA_CAPS */
	private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/** ALPHA */
	private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	/** NUM */
	private static final String NUM = "0123456789";
	/** SPL_CHARS */
	private static final String SPL_CHARS = "!@#$%^&*_=+-/";

	/***
	 * Generate a random password or random string
	 * 
	 * @param minLen
	 * @param maxLen
	 * @param noOfCAPSAlpha
	 * @param noOfDigits
	 * @param noOfSplChars
	 * @return String suitable for use as a temporary password
	 */
	public static String random(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
		if (minLen > maxLen) {
			throw new IllegalArgumentException("Min. Length > Max. Length!");
		}
		if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen) {
			throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
		}
		
		Random rnd = new Random();
		int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
		char[] pswd = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
		}
		for (int i = 0; i < len; i++) {
			if (pswd[i] == 0) {
				pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
			}
		}
		return chars2String(pswd);
	}

	private static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0) {
			// TODO
		}
		return index;
	}
	
	private static String chars2String(char[] chars) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			stringBuffer.append(chars[i]);
		}
		return stringBuffer.toString();
	}
}
