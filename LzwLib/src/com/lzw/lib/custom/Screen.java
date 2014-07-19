package com.lzw.lib.custom;

import android.content.Context;
import android.util.DisplayMetrics;

public final class Screen {
	
//	private static final String TAG = "Screen";
	
	public int mWidth = 0; // 屏幕宽度
	public int mHeight = 0; // 屏幕高度
//	public float mDensity = 0; // 屏幕密度 0.75 / 1.0 / 1.5 / 2.0
//	public int mDensityDpi = 0; // 像素密度 120 / 160 / 240 / 320
	
	public Screen(Context context) {
		getDefaultScreenInfos(context);
	}
	
	public void getDefaultScreenInfos(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		mWidth = metrics.widthPixels;
		mHeight = metrics.heightPixels;
//		mDensity = metrics.density;
//		mDensityDpi = metrics.densityDpi;
		
//		Display display = ((WindowManager) Gl.Ct().getSystemService(Gl.Ct().WINDOW_SERVICE)).getDefaultDisplay();
//		mWidth = display.getWidth();
//		mHeight = display.getHeight();
	}
}