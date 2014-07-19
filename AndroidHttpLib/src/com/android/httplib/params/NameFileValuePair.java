package com.android.httplib.params;

public class NameFileValuePair {

	private String name = "";
	private String uri = "";

	public NameFileValuePair(String name, String uri) {
		setName(name);
		setUri(uri);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			name = "";

		this.name = name;
	}

	public String getData() {
		return this.uri;
	}

	public void setUri(String uri) {
		if (uri == null)
			uri = "";

		this.uri = uri;
	}
}
