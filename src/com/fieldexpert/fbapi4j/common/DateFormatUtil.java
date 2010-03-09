package com.fieldexpert.fbapi4j.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO Improve?
public class DateFormatUtil {
	private static final String FB_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static DateFormat getParser() {
		return new SimpleDateFormat(FB_DATE);
	}

	public static String format(Date date) {
		return date == null ? null : getParser().format(date);
	}

	public static Date parse(String date) {
		if (date == null || date.equals("")) {
			return null;
		}
		try {
			return getParser().parse(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
