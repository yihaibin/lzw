package com.lzw.zmm.fragment.barcode;

import android.os.Bundle;

import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.util.res.ResLoader;

public class BarcodeFragment extends BaseFragment {

	public BarcodeFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		setFragmentContentView(R.layout.barcode_fragment);
		System.out.println("~~onActivityCreated");
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
	protected void initTitleView() {
		
		addTitleMiddleTextView(R.string.barcode_scanner);
	}
	
	@Override
	protected void initContentView() {
		
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		
		super.onHiddenChanged(hidden);
		System.out.println("~~onHiddenChanged "+hidden);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}
