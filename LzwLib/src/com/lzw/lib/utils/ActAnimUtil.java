package com.lzw.lib.utils;


import android.app.Activity;

public class ActAnimUtil {

	public static void startActAnim(Activity activity, int inAnim, int outAnim) {

		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT) {
			new Object() {
				public void overridePendingTransition(Activity act, int i, int j) {
					act.overridePendingTransition(i, j);
				}
			}.overridePendingTransition(activity, inAnim, outAnim);
		}
	}
}
