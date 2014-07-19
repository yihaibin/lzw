package com.android.httplib;

import android.util.Log;

public final class LogHttp {

	private static final String SEPERATE = "=========";
	private static final boolean mDevelopMode = true;

	private LogHttp() {
	}

	public static boolean isDevelopMode() {
		return mDevelopMode;
	}

	public static int v(String tag, String msg) {
		if (mDevelopMode) {
			return Log.v(tag, msg);
		} else {
			return 0;
		}
	}

	public static int v(String tag, String msg, Throwable tr) {
		if (mDevelopMode) {
			return Log.v(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg) {
		if (mDevelopMode) {
			return Log.d(tag, msg);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg, Throwable tr) {
		if (mDevelopMode) {
			return Log.d(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg) {
		if (mDevelopMode) {
			return Log.i(tag, msg);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg, Throwable tr) {
		if (mDevelopMode) {
			return Log.i(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static void w(String tag, String log) {
		if (mDevelopMode) {
			Log.w(tag, log);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (mDevelopMode) {
			Log.e(tag, msg, tr);
		}
	}

	public static void d(String tag, Throwable e) {
		if (mDevelopMode) {
			Log.d(tag, SEPERATE + e.getClass().getSimpleName() + SEPERATE, e);
		}
	}

	public static void e(String tag, Throwable e) {
		if (mDevelopMode) {
			Log.e(tag, SEPERATE + e.getClass().getSimpleName() + SEPERATE, e);
		}
	}

	public static void e(String tag, String log) {
		if (mDevelopMode)
			Log.e(tag, log);
	}
}
