package com.lzw.zmm.fragment.barcode;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.util.res.ResLoader;
import com.lzw.zmm.zxing.activity.MipcaActivityCapture;


public class BarcodeFragment extends BaseFragment{
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		setFragmentContentView(R.layout.barcode_fragment);
	}
	
	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_barcode);
	}

	@Override
	public int getIvResId() {
		return R.drawable.tab_icon_scan_selector;
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
		
		addTitleMiddleTextView("条形码扫描");
	}
	
	@Override
	protected void initContentView() {
		
		TextView tvSearch = (TextView) findViewById(R.id.tvSearch);
		tvSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				MipcaActivityCapture.startActivity(getActivity());
			}
		});
	}


	
}
