package com.lzw.zmm.bean;

import java.io.Serializable;

public class ChannelItem implements Serializable {
	private static final long serialVersionUID = -6465237897027410019L;

	public int mId;
	public String mName;
	public int mOrderId;
	public Integer mSelected;

	public ChannelItem() {
	}

	public ChannelItem(int id, String name, int orderId, int selected) {
		mId = id;
		mName = name;
		mOrderId = orderId;
		mSelected = selected;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getOrderId() {
		return mOrderId;
	}

	public Integer getSelected() {
		return mSelected;
	}

	public void setId(int id) {
		mId = id;
	}

	public void setName(String name) {
		mName = name;
	}

	public void setOrderId(int id) {
		mOrderId = id;
	}

	public void setSelected(Integer selected) {
		mSelected = selected;
	}

	public String toString() {
		return "ChannelItem [id=" + mId + ", name=" + mName + ", selected=" + mSelected + "]";
	}
}