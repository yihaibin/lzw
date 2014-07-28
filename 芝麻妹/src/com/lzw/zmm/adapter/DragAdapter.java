package com.lzw.zmm.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzw.zmm.R;
import com.lzw.zmm.bean.ChannelItem;

public class DragAdapter extends BaseAdapter {
	private final static String TAG = DragAdapter.class.getSimpleName();
	
	/** 是否显示底部的ITEM */
	private boolean mIsItemShow = false;
	private Context mContext;
	/** 控制的postion */
	private int mHoldPosition;
	/** 是否改变 */
	private boolean mIsChanged = false;
	/** 是否可见 */
	boolean mIsVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public List<ChannelItem> mChannelList;
	/** TextView 频道内容 */
	private TextView mTvItemContent;
	/** 要删除的position */
	public int mRemove_position = -1;

	public DragAdapter(Context context, List<ChannelItem> channelList) {
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
		View view = LayoutInflater.from(mContext).inflate(R.layout.subscribe_category_item, null);
		mTvItemContent = (TextView) view.findViewById(R.id.text_item);
		ChannelItem channel = getItem(position);
		mTvItemContent.setText(channel.getName());
		if ((position == 0) || (position == 1)) {
			// item_text.setTextColor(context.getResources().getColor(R.color.black));
			mTvItemContent.setEnabled(false);
		}
		if (mIsChanged && (position == mHoldPosition) && !mIsItemShow) {
			mTvItemContent.setText("");
			mTvItemContent.setSelected(true);
			mTvItemContent.setEnabled(true);
			mIsChanged = false;
		}
		if (!mIsVisible && (position == -1 + mChannelList.size())) {
			mTvItemContent.setText("");
			mTvItemContent.setSelected(true);
			mTvItemContent.setEnabled(true);
		}
		if (mRemove_position == position) {
			mTvItemContent.setText("");
		}
		return view;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItem channel) {
		mChannelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		mHoldPosition = dropPostion;
		ChannelItem dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			mChannelList.add(dropPostion + 1, dragItem);
			mChannelList.remove(dragPostion);
		} else {
			mChannelList.add(dropPostion, dragItem);
			mChannelList.remove(dragPostion + 1);
		}
		mIsChanged = true;
		notifyDataSetChanged();
	}

	/** 获取频道列表 */
	public List<ChannelItem> getChannnelLst() {
		return mChannelList;
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

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		mIsItemShow = show;
	}
}