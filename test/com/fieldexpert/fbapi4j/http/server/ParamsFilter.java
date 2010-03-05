package com.fieldexpert.fbapi4j.http.server;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ParamsFilter extends Filter {

	public String description() {
		return "Parses request parameters.";
	}

	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		ParameterParser parser = new ParameterParser();
		exchange.setAttribute("parameters", parser.parse(exchange));
		chain.doFilter(exchange);
	}

}
