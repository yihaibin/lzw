package com.lzw.zmm.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.androidex.adapter.ExAdapter;
import com.androidex.adapter.ExViewHolder;
import com.androidex.adapter.ExViewHolderBase;
import com.androidex.asyncimage.AsyncImageView;
import com.androidex.util.TimeUtil;
import com.lzw.zmm.R;
import com.lzw.zmm.bean.Record;

public class RecordAdapter extends ExAdapter<Record>{

	@Override
	protected ExViewHolder getViewHolder(int position) {
		
		return new RecordViewHolder();
	}

	private class RecordViewHolder extends ExViewHolderBase{
		
		private AsyncImageView mAivRecordCover;
		private TextView mTvRecordTitle, mTvRecordTime;
		private View mClicker;
		
		@Override
		public int getConvertViewRid() {
			
			return R.layout.item_record;
		}

		@Override
		public void initConvertView(View convertView) {
			
			mAivRecordCover = (AsyncImageView) convertView.findViewById(R.id.aivRecordCover);
			mTvRecordTitle = (TextView) convertView.findViewById(R.id.tvRecordTitle);
			mTvRecordTime = (TextView) convertView.findViewById(R.id.tvRecordTime);
			mClicker = convertView.findViewById(R.id.llClicker);
			mClicker.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					callbackOnItemViewClickListener(mPosition, v);
				}
			});
		}

		@Override
		public void invalidateConvertView() {
			
			Record record = getItem(mPosition);
			mTvRecordTitle.setText(record.getTitle());
			mTvRecordTime.setText(TimeUtil.getSimpleTimeCharSeq(record.getTimeStamp()));
		}
		
	}
}
