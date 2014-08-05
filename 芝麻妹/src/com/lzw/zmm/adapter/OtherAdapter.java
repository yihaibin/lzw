package com.lzw.zmm.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzw.zmm.R;
import com.lzw.zmm.bean.ChannelItem;

public class OtherAdapter extends BaseAdapter {

	private Context mContext;
	public List<ChannelItem> mChannelList;
	private TextView mTvItemContent;
	/** 是否可见 */
	boolean mIsVisible = true;
	/** 要删除的position */
	public int mRemove_position = -1;

	public OtherAdapter(Context context, List<ChannelItem> channelList) {
		mContext = context;
		mChannelList = channelList;
	}

	@Override
	public int getCount() {
		return mChannelList == null ? 0 : mChannelList.size();
	}

	@Override
	public ChannelItem getItem(int position) {
		if (mChannelList != null && mChannelList.size() != 0) {
			return mChannelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.channel_category_item, null);
		mTvItemContent = (TextView) view.findViewById(R.id.text_item);
		ChannelItem channel = getItem(position);
		mTvItemContent.setText(channel.getName());
		if (!mIsVisible && (position == -1 + mChannelList.size())) {
			mTvItemContent.setText("");
		}
		if (mRemove_position == position) {
			mTvItemContent.setText("");
		}
		return view;
	}

	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return mChannelList;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		mChannelList.add(channel);
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		mRemove_position = position;
		notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		mChannelList.remove(mRemove_position);
		mRemove_position = -1;
		notifyDataSetChanged();
	}

	/** 设置频道列表 */
	public void setListDate(List<ChannelItem> list) {
		mChannelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return mIsVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		mIsVisible = visible;
	}
}