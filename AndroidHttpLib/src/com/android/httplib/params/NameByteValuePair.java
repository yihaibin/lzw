package com.android.httplib.params;

public class NameByteValuePair {

	private String name = "";
	private byte[] data;

	public NameByteValuePair() {
	}

	public NameByteValuePair(String name, byte[] data) {
		setName(name);
		setData(data);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			name = "";

		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
