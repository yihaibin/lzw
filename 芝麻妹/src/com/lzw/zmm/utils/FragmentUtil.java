package com.lzw.zmm.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {
	
	public static void startFragment(FragmentActivity activity, String tagName, int viewID, Fragment fragment) {
		FragmentManager manager = activity.getSupportFragmentManager();
		if (manager.findFragmentByTag(tagName) == null) {
			FragmentTransaction trans = manager.beginTransaction();
			trans.replace(viewID, fragment, tagName);
			trans.commit();
		}
	}
}
