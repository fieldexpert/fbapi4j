package com.fieldexpert.fbapi4j;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

public class CustomColumnTest {
	
	/***
	 * If custom columns are specified, ensure the case handler correctly adds them to its list of 
	 * supported columns.
	 */
	@Test
	public void testCustColumnSetup () {
		Properties propsWithCustomColumns = new Properties();
		propsWithCustomColumns.setProperty(CaseHandler.PROP_COLUMNS, "some-column-a" + CaseHandler.FIELD_CSV_DELIM + "some-column-b");
		CaseHandler caseHandler = new CaseHandler(null, null, null, propsWithCustomColumns);

		assertTrue("The case handler should support 'some-column-a'", caseHandler.getSupportedColumns().contains("some-column-a"));
		assertTrue("The case handler should support 'some-column-b'", caseHandler.getSupportedColumns().contains("some-column-b"));
	}
	
	/***
	 * Test the custom columns logic doesn't affect the default columns handling
	 */
	@Test
	public void testDefaultColumns() {
		CaseHandler caseHandler = new CaseHandler(null, null, null, new Properties());
		for (String currentDefaultColumn : CaseHandler.DEFAULT_COLUMNS) {
			assertTrue(String.format("The case handler should support the default column '%s'", currentDefaultColumn),
					caseHandler.getSupportedColumns().contains(currentDefaultColumn));
		}
	}
}
