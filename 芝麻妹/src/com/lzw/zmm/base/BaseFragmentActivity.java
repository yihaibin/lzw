package com.lzw.zmm.base;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

abstract public class BaseFragmentActivity extends FragmentActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSPARENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		
		init();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		
		init();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		
		init();
	}


	private void init() {
		initData();
		findViews();
		setViewsValue();
	}
	
	abstract protected void initData();
	abstract protected void findViews();
	abstract protected void setViewsValue();
}
