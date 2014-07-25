package com.lzw.zmm.fragment.record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzw.lib.util.res.ResLoader;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;

public class RecordFragment extends BaseFragment {

	public RecordFragment() {
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.record_fragment, container, false);
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

	}

	@Override
	protected void findViews() {
	}

	@Override
	protected void setViewsValue() {

	}
}
