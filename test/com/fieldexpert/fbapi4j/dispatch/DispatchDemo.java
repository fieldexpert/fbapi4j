package com.fieldexpert.fbapi4j.dispatch;

import com.fieldexpert.fbapi4j.Configuration;
import com.fieldexpert.fbapi4j.common.Util;

public class DispatchDemo {

	public static void main(String[] args) throws Exception {
//		String email = args[0];
	//	String password = args[1];

		Util util = new Util();

		Configuration conf = new Configuration();

		conf.setProperty("endpoint", "http://localhost:8080/uploader/uploader");
		conf.setProperty("email", "email");
		conf.setProperty("password", "password");
		conf.setProperty("path", "uploader");

		Dispatch dispatch = conf.buildDispatch();

		Response resp;
		//Map<String, String> response;

		resp = dispatch.invoke(new Request(util.map("foo", "bar")).attach("foo.txt", "text/html", "<htm>glory</html>"));
		
		System.out.println(util.data(resp.getDocument(), "file"));
	}
}
