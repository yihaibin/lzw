package com.lzw.zmm.fragment.barcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzw.lib.util.res.ResLoader;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;

public class BarcodeFragment extends BaseFragment {

	public BarcodeFragment() {
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.barcode_fragment, container, false);
	}

	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_barcode);
	}

	@Override
	public int getViewId() {
		return R.id.main_tab_fragment_barcode;
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
