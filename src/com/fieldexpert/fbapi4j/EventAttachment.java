package com.fieldexpert.fbapi4j;

import java.io.InputStream;
import java.net.URL;

import com.fieldexpert.fbapi4j.common.Attachment;

public class EventAttachment extends Attachment {
	private URL url;

	EventAttachment(String name, URL url) {
		super(name);
		this.url = url;
	}

	@Override
	public String getType() {
		return getType(getContent());
	}

	@Override
	public InputStream getContent() {
		try {
			return url.openStream();
		} catch (Exception e) {
			throw new RuntimeException("Cannot fetch attachment");
		}
	}

}
