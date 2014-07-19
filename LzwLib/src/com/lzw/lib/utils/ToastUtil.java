package com.lzw.lib.utils;

import android.widget.Toast;

import com.lzw.lib.ex.AppEx;
import com.lzw.lib.util.res.ResLoader;

public class ToastUtil {

	private static Toast mToastKeeper;
	private static String mContent = "";
	
	public static void makeToast(String content) {
		
		if (mToastKeeper == null || !mContent.equals(content)) {
			mContent = content;
			mToastKeeper = Toast.makeText(AppEx.ct(), content, Toast.LENGTH_SHORT);
		}
		
		mToastKeeper.show();
	}
	
	public static void makeToast(String content, int duration) {
		if (mToastKeeper == null || !mContent.equals(content)) {
			mContent = content;
			mToastKeeper = Toast.makeText(AppEx.ct(), content, duration);
		}
		mToastKeeper.show();
	}
	
	public static void makeToast(int strResId) {
		String content = ResLoader.getString(strResId);
		if (mToastKeeper == null || !mContent.equals(content)) {
			mContent = content;
			mToastKeeper = Toast.makeText(AppEx.ct(), mContent, Toast.LENGTH_SHORT);
		}
		mToastKeeper.show();
	}
}
