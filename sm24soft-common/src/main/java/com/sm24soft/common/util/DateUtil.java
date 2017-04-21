package com.sm24soft.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil extends org.apache.commons.lang.time.DateUtils {

	/**
	 * Get current date time
	 * 
	 * @return
	 */
	public static Date now() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
}
