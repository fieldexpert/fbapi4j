package com.fieldexpert.fbapi4j.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StringUtil {

	public static String collectionToCommaDelimitedString(Collection<?> col) {
		return collectionToDelimitedString(col, ",");
	}

	public static String collectionToDelimitedString(Collection<?> col, String del) {
		if (col == null || col.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = col.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(del);
			}
		}
		return sb.toString();
	}

	public static List<String> commaDelimitedStringToSet(String str) {
		if (str == null) {
			return new ArrayList<String>(0);
		}
		List<String> result = new ArrayList<String>();

		String[] pieces = str.split(",");
		for (String piece : pieces) {
			result.add(piece);
		}
		return result;
	}

}
