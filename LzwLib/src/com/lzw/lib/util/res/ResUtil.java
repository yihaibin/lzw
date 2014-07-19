package com.lzw.lib.util.res;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.NinePatch;

public class ResUtil {

	/**
	 * Res Type defines
	 */
	public static final String KTypeDefId = "id";
	public static final String KTypeDefString = "string";
	public static final String KTypeDefDrawable = "drawable";
	public static final String KTypeDefLayout = "layout";
	
	private static Options mOpt = new Options();

	static {
		mOpt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		mOpt.inDither = false; // 不自动转成16位色
		mOpt.inPurgeable = true;
		mOpt.inInputShareable = true;
	}

	public static int getIdentifier(Context context, String id, String type) {
		return context.getResources().getIdentifier(id, type, context.getPackageName());
	}

	public static String getString(Context context, int resId) {
		return context.getString(resId);
	}

	public static Bitmap getBitmap(Context context, int resId) {
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, mOpt);
	}
	
	public static Bitmap getBitmap(Context context, int resId, Options opt) {
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public static Bitmap getBitmap(String filePath) {
		return BitmapFactory.decodeFile(filePath, mOpt);
	}
	
	public static Bitmap getBitmap(String filePath, Options opt) {
		return BitmapFactory.decodeFile(filePath, opt);
	}
	


	/**
	 * 不载入内存, 只读取图片的信息
	 * @param resId
	 * @return Options对象opt 使用方法: 如获取高度, opt.outHeight
	 */
	public static Options getBitmapInfo(Context context, int resId) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		InputStream is = context.getResources().openRawResource(resId);
		BitmapFactory.decodeStream(is, null, opt);
		return opt;
	}

	/**
	 * 不载入内存, 只读取图片的信息
	 * @param resId
	 * @return Options对象opt 使用方法: 如获取高度, opt.outHeight
	 */
	public static Options getBitmapInfo(String filePath) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opt);
		return opt;
	}

	public static int getColor(Context context, int resId) {
		return context.getResources().getColor(resId);
	}

	public static float getDimention(Context context, int resId) {
		return context.getResources().getDimension(resId);
	}

	public NinePatch getNinePatchBmp(Context context, int resId) {
		Bitmap tmpBmp = getBitmap(context, resId);
		return new NinePatch(tmpBmp, tmpBmp.getNinePatchChunk(), null);
	}
}
