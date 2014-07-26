package com.lzw.zmm.util.res;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

import com.androidex.context.ExApplication;

/**
 * 一定要继承自ExApplication的Application才能使用这个工具类
 */
public class ResLoader {
	
	private static Context sCt = null;
	
	static{
		sCt = ExApplication.getContext();
	}
	
	
	public static int getIdentifier(String id, String type) {
		return ResUtil.getIdentifier(sCt, id, type);
	}

	public static String getString(int resId) {
		return ResUtil.getString(sCt, resId);
	}

	public static Bitmap getBitmap(int resId) {
		return ResUtil.getBitmap(sCt, resId);
	}

	public static Bitmap getBitmap(String filePath) {
		return ResUtil.getBitmap(filePath);
	}
	

	/**
	 * 不载入内存, 只读取图片的信息
	 * @param resId
	 * @return Options对象opt 使用方法: 如获取高度, opt.outHeight
	 */
	public static Options getBitmapInfo(int resId) {
		return ResUtil.getBitmapInfo(sCt, resId);
	}

	/**
	 * 不载入内存, 只读取图片的信息
	 * @param resId
	 * @return Options对象opt 使用方法: 如获取高度, opt.outHeight
	 */
	public static Options getBitmapInfo(String filaPath) {
		return ResUtil.getBitmapInfo(filaPath);
	}

	public static int getColor(int resId) {
		return ResUtil.getColor(sCt, resId);
	}

	public static float getDimention(int resId) {
		return ResUtil.getDimention(sCt, resId);
	}
}
