package com.fieldexpert.fbapi4j.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class IOUtil {

	public static String string(File file) throws java.io.IOException {
		return string(new FileInputStream(file));
	}

	public static String string(InputStream is) throws IOException {
		final char[] buffer = new char[1024];
		StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(is, "UTF-8");
		int read;
		do {
			read = in.read(buffer, 0, buffer.length);
			if (read > 0) {
				out.append(buffer, 0, read);
			}
		} while (read >= 0);

		return out.toString();
	}

}
