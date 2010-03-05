package com.fieldexpert.fbapi4j.http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EchoHandler implements HttpHandler {

	@SuppressWarnings("unchecked")
	public void handle(HttpExchange exchange) throws IOException {
		Map<String, String> params = (Map<String, String>) exchange.getAttribute("parameters");
		String response = params.toString();
		exchange.sendResponseHeaders(200, response.length());

		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
