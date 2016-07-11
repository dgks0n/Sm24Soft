package com.sm24soft.common.util;

import org.apache.commons.lang.StringUtils;

public class StringFormatUtil {

	private static final String PAD_CHAR = "0";
	
	public static String formatNumberAsString(final int PADS, Integer value) {
		String input = String.valueOf(value);
		return StringUtils.leftPad(input, PADS, PAD_CHAR);
	}
	
	public static String formatString(final int PADS, String value) {
		return StringUtils.leftPad(value, PADS, PAD_CHAR);
	}
}
