package com.lzw.lib.ex;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

abstract public class SplashActivityEx extends Activity {

	protected Handler mHandler;
	
	protected void setSplashParams(final Class<?> cls, long delay) {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				goPastSplash(cls);
			}
		};
		
		mHandler.sendEmptyMessageDelayed(0, delay);
	}
	
	protected void goPastSplash(Class<?> cls) {
		Intent mainIntent = new Intent(this, cls);
		startActivity(mainIntent);
		finish();
	}

	@Override
	public void finish() {
		super.finish();
		startFinishAnim();
	}
	
	abstract protected void startFinishAnim();
}
