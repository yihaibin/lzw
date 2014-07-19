package com.lzw.zmm.utils;

import android.app.Activity;

import com.lzw.lib.utils.ActAnimUtil;
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

		ActAnimUtil.startActAnim(activity, inAnim, outAnim);
	}
}
