package com.fieldexpert.fbapi4j.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class DateFormatUtilTest {
	private static final String FB_DATE = "1984-03-30T00:00:00Z";
	private Date MAR_30_1984;

	@Before
	public void setup() {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.set(1984, 2, 30, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		MAR_30_1984 = cal.getTime();
	}

	@Test
	public void format() {
		assertEquals(FB_DATE, DateFormatUtil.format(MAR_30_1984));
	}

	@Test
	public void parse() {
		assertEquals(MAR_30_1984, DateFormatUtil.parse(FB_DATE));
	}

	@Test(expected = RuntimeException.class)
	public void garbage() {
		DateFormatUtil.parse("garbage");
		fail();
	}

	@Test
	public void formatNull() {
		assertNull(DateFormatUtil.format(null));
	}

	@Test
	public void nullOrEmpty() {
		assertNull(DateFormatUtil.parse(null));
		assertNull(DateFormatUtil.parse(""));
	}
}
