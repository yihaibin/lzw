package com.lzw.zmm.utils;

import android.app.Activity;

import com.lzw.zmm.R;

public class ActAnimMgr {

	public enum TActAnimType {
		ESplash,
		ELeftIn,
		ERightOut,
		EBottomInAndOut,
	}

	public static void startActAnim(Activity activity, TActAnimType type) {
		int inAnim = 0;
		int outAnim = 0;

		switch (type) {
			case ESplash: {
				inAnim = R.anim.fade_in;
				outAnim = R.anim.hold_splash;
			}
				break;
			case ELeftIn: {
				inAnim = R.anim.left_in;
				outAnim = R.anim.left_out;
			}
				break;
			case ERightOut: {
				inAnim = R.anim.right_in;
				outAnim = R.anim.right_out;
			}
				break;
			case EBottomInAndOut: {
				inAnim = R.anim.push_bottom_in;
				outAnim = R.anim.push_bottom_out;
			}
				break;
			default:
				break;
		}

		startActAnim(activity, inAnim, outAnim);
	}
	
	private static void startActAnim(Activity activity, int inAnim, int outAnim) {

		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT) {
			new Object() {
				public void overridePendingTransition(Activity act, int i, int j) {
					act.overridePendingTransition(i, j);
				}
			}.overridePendingTransition(activity, inAnim, outAnim);
		}
	}
}
