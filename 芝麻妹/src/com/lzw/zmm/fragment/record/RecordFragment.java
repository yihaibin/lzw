package com.lzw.zmm.fragment.record;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidex.util.ViewUtil;
import com.lzw.zmm.R;
import com.lzw.zmm.adapter.RecordAdapter;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.db.dao.RecordDao;
import com.lzw.zmm.util.res.ResLoader;

public class RecordFragment extends BaseFragment {

	private RecordDao mRecordDao;
	private RecordAdapter mRecordAdapter;
	
	public RecordFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		setFragmentContentView(R.layout.record_fragment);
	}

	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_record);
	}

	@Override
	public int getViewId() {
		return R.id.main_tab_fragment_record;
	}

	@Override
	protected void initData() {

		mRecordDao = new RecordDao(getActivity());
		mRecordAdapter = new RecordAdapter();
		mRecordAdapter.setData(mRecordDao.getAllRecord());
	}

	@Override
	protected void initTitleView() {
	
		addTitleMiddleTextView(R.string.scanner_record);
	}
	
	@Override
	protected void initContentView() {
		
		final ListView lvRecords = (ListView) findViewById(R.id.lvRecords);
		lvRecords.addHeaderView(ViewUtil.inflateSpaceViewBydp(3));
		lvRecords.addFooterView(ViewUtil.inflateSpaceViewBydp(3));
		lvRecords.setAdapter(mRecordAdapter);
		
		ImageView ivTop = (ImageView) findViewById(R.id.ivToTop);
		ivTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!mRecordAdapter.isEmpty())
					lvRecords.setSelectionFromTop(0, 0);
			}
		});
	}



}
