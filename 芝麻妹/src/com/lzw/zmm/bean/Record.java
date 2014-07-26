package com.lzw.zmm.bean;

import com.androidex.util.TextUtil;

public class Record {

	private int id;
	private String title = TextUtil.TEXT_EMPTY;
	private String coverUrl = "";
	private long timeStamp;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		
		return title;
	}
	
	public void setTitle(String title) {
	
		if(title == null)
			title = TextUtil.TEXT_EMPTY;
		
		this.title = title;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}
	
	public void setCoverUrl(String coverUrl) {
		
		if(coverUrl == null)
			coverUrl = TextUtil.TEXT_EMPTY;
		
		this.coverUrl = coverUrl;
	}
	
	public long getTimeStamp() {
		
		return timeStamp;
	}
	
	public void setTimeStamp(long timeStamp) {
		
		this.timeStamp = timeStamp;
	}
}
