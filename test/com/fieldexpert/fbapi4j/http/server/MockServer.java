package com.fieldexpert.fbapi4j.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class MockServer {
	private final HttpServer server;

	public MockServer(String path) {
		this("127.0.0.1", 9000, path);
	}

	public MockServer(int port, String path) {
		this("127.0.0.1", port, path);
	}

	public MockServer(String host, int port, String path) {
		InetSocketAddress address = new InetSocketAddress(host, port);
		try {
			server = HttpServer.create(address, 0);
			HttpContext context = server.createContext(path, new EchoHandler());
			context.getFilters().add(new ParamsFilter());
		} catch (IOException e) {
			throw new RuntimeException("Unable to create server on " + host + ":" + port);
		}
	}

	public void start() {
		server.start();
	}

	public void stop() {
		if (server == null) {
			return;
		}
		server.stop(0);
	}

}
