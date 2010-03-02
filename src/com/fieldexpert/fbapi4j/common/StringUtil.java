package com.fieldexpert.fbapi4j.common;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {

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

	public static String collectionToCommaDelimitedString(Collection<?> col) {
		return collectionToDelimitedString(col, ",");
	}
}
