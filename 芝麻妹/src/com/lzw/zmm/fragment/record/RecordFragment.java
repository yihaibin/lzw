package com.lzw.zmm.fragment.record;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidex.adapter.OnItemViewClickListener;
import com.androidex.util.ViewUtil;
import com.lzw.zmm.R;
import com.lzw.zmm.adapter.RecordAdapter;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.bean.Record;
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
	public void onStart() {
		
		super.onStart();
		//mRecordAdapter.setData(mRecordDao.getAllRecord());//数据库已写好，业务流程写完打开该注释即可
		mRecordAdapter.setData(getTestAllRecord());//临时测试数据
		mRecordAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 返回测试数据集合,业务流程写好后，删除
	 * @return
	 */
	private List<Record> getTestAllRecord(){
		
		ArrayList<Record> records = new ArrayList<Record>();
		for(int i=0; i<50; i++){
			
			Record record = new Record();
			record.setId(i+1);
			record.setTitle((i+1)+", 补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml");
			record.setCoverUrl("http://www.asdfjasdfj.com");
			record.setTimeStamp(System.currentTimeMillis()+i+1);
			records.add(record);
		}
		return records;
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
		mRecordAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
			@Override
			public void onViewClick(int position, View v) {
				
				//TODO
				showToast("item position = "+position);
			}
		});
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
