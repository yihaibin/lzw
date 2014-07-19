package com.lzw.lib.ex;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import com.lzw.lib.custom.Screen;

/**
 * SDK版本最高支持到17
 * @author ForGetMan
 */
abstract public class AppEx extends Application {
	
	protected static Context sContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
	}

	/**
	 * 返回getApplicationContext()
	 * @return
	 */
	public static Context ct() {
		return sContext;
	}
	
	// 屏幕参数
	protected static Screen mScreenParam = null;
	public static Screen getScreenParams() {
		if (mScreenParam == null) {
			mScreenParam = new Screen(sContext);
		}
		return mScreenParam;
	}
	public static void resetScreenParams() {
		getScreenParams().getDefaultScreenInfos(sContext);
	}
	
	public static ContentResolver getExContentResolver(){
		return sContext.getContentResolver();
	}
}
