package com.lzw.zmm.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.database.SQLException;

import com.lzw.zmm.db.SQLHelper;
import com.lzw.zmm.db.dao.ChannelDao;

public class ChannelManage {
	
	public static ChannelManage mChannelManage;
	
	// 默认的用户选择频道列表
	public static List<ChannelItem> mDefaultUserChannels;
	// 默认的其他频道列表
	public static List<ChannelItem> mDefaultOtherChannels;
	private ChannelDao mChannelDao;

	// 判断数据库中是否存在用户数据
	private boolean userExist = false;

	static {
		mDefaultUserChannels = new ArrayList<ChannelItem>();
		mDefaultUserChannels.add(new ChannelItem(1, "首页", 1, 1));
		mDefaultUserChannels.add(new ChannelItem(2, "聚美优品", 2, 1));
		mDefaultUserChannels.add(new ChannelItem(3, "乐蜂网", 3, 1));
		mDefaultUserChannels.add(new ChannelItem(4, "唯品会", 4, 1));
		mDefaultUserChannels.add(new ChannelItem(5, "京东商城", 5, 1));
		mDefaultUserChannels.add(new ChannelItem(6, "银泰网", 6, 1));
		mDefaultUserChannels.add(new ChannelItem(7, "天猫", 7, 1));
		
		mDefaultOtherChannels = new ArrayList<ChannelItem>();
		mDefaultOtherChannels.add(new ChannelItem(8, "苏宁易购", 1, 0));
		mDefaultOtherChannels.add(new ChannelItem(9, "梦芭莎", 2, 0));
	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (mChannelDao == null) {
			mChannelDao = new ChannelDao(paramDBHelper.getContext());
		}
		return;
	}

	public static ChannelManage getManage(SQLHelper dbHelper) throws SQLException {
		if (mChannelManage == null) {
			mChannelManage = new ChannelManage(dbHelper);
		}
		return mChannelManage;
	}

	/**
	 * 清除所有的频道
	 */
	public void deleteAllChannel() {
		mChannelDao.clearFeedTable();
	}

	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = mChannelDao.listCache(SQLHelper.SELECTED + "= ?", new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return mDefaultUserChannels;
	}

	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = mChannelDao.listCache(SQLHelper.SELECTED + "= ?", new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if (userExist) {
			return list;
		}
		cacheList = mDefaultOtherChannels;
		return (List<ChannelItem>) cacheList;
	}

	/**
	 * 保存用户频道到数据库
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			mChannelDao.addCache(channelItem);
		}
	}

	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			mChannelDao.addCache(channelItem);
		}
	}

	/**
	 * 初始化数据库内的频道数据
	 */
	private void initDefaultChannel() {
		deleteAllChannel();
		saveUserChannel(mDefaultUserChannels);
		saveOtherChannel(mDefaultOtherChannels);
	}
}
