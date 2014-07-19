package com.lzw.zmm.startup;

import android.os.Bundle;

import com.lzw.lib.ex.SplashActivityEx;
import com.lzw.zmm.App;
import com.lzw.zmm.R;
import com.lzw.zmm.fragment.FragmentTabs;
import com.lzw.zmm.utils.ActAnimMgr;
import com.lzw.zmm.utils.ActAnimMgr.TActAnimType;

public class SplashScreen extends SplashActivityEx {

	private final long KSplashDelay = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		App.resetScreenParams();
		
		setSplashParams(FragmentTabs.class, KSplashDelay);
	}

	@Override
	protected void startFinishAnim() {
		ActAnimMgr.startActAnim(this, TActAnimType.ESplash);		
	}

}
