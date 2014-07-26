package com.lzw.zmm;

import android.graphics.Color;

import com.androidex.activity.ExActivityParams;
import com.androidex.asyncimage.AsyncImageView;
import com.androidex.context.ExApplication;
import com.androidex.http.task.HttpTask;
import com.androidex.util.DensityUtil;
import com.androidex.util.DeviceUtil;
import com.androidex.util.StorageUtil;

public class App extends ExApplication {

	@Override
	public void onCreate() {
		
		super.onCreate();
		init();
	}

	private void init() {
		
		//设置sdcard应用的主目录
		StorageUtil.setAppHomeDir("zhimamei");
		
		// 设置HttpTask缓存目录
		HttpTask.setCacheDir(getAppCacheSubDir("httptask"));
		
		// 设置图片加载框架
		AsyncImageView.setAsyncImageParams((int) (DeviceUtil.getRuntimeMaxMemory() / 5),StorageUtil.getAppPicDir());
		
		/* title bar 相关 */
		
		// 设置整个应用的背景
		ExActivityParams.setBackgroundResId(android.R.color.white);// 整个app背景
		// 设置标题栏背景和高度
		ExActivityParams.setTitleViewBackgroundResId(R.color.pink);// 标题栏背景
		ExActivityParams.setTitleViewHeight(DensityUtil.dip2px(48));// 标题栏高度
		// 设置标题栏按钮样式
		ExActivityParams.setTitleViewClickerBgResId(R.drawable.bg_trans_black20_selector);// 标题栏点击按钮的背景
		//ExActivityParams.setTitleViewImageClickerBackIconResId(R.drawable.ic_back);// back按钮
		ExActivityParams.setTitleViewTextClickerHoriPadding(DensityUtil.dip2px(10));// 文字按钮水平间距6dp
		// 标题栏文字大小和颜色
		ExActivityParams.setTitleViewTitleTextSize(21);// dp
		ExActivityParams.setTitleViewClickerTextSize(16);
		ExActivityParams.setTitleViewTextColor(Color.WHITE);
		ExActivityParams.setTitleViewMainTextSize(16);// dp
		ExActivityParams.setTitleViewSubTextSize(12);// dp
	}

}
