package com.lzw.zmm.fragment.homepage;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidex.activity.ExActivity;
import com.lzw.zmm.App;
import com.lzw.zmm.R;
import com.lzw.zmm.adapter.DragAdapter;
import com.lzw.zmm.adapter.OtherAdapter;
import com.lzw.zmm.bean.ChannelItem;
import com.lzw.zmm.bean.ChannelMgr;
import com.lzw.zmm.view.DragGrid;
import com.lzw.zmm.view.OtherGridView;

public class ChannelActivity extends ExActivity implements OnItemClickListener {
	/** 用户栏目的GRIDVIEW */
	private DragGrid mUserGridView;
	/** 其它栏目的GRIDVIEW */
	private OtherGridView mOtherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	DragAdapter mUserAdapter;
	/** 其它栏目对应的适配器 */
	OtherAdapter mOtherAdapter;
	/** 其它栏目列表 */
	ArrayList<ChannelItem> mOtherChannelList = new ArrayList<ChannelItem>();
	/** 用户栏目列表 */
	ArrayList<ChannelItem> mUserChannelList = new ArrayList<ChannelItem>();
	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
	boolean mIsMove = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_activity);
	}

	@Override
	protected void initData() {
		mUserChannelList = ((ArrayList<ChannelItem>) ChannelMgr.getManage(App.getSQLHelper()).getUserChannel());
		mOtherChannelList = ((ArrayList<ChannelItem>) ChannelMgr.getManage(App.getSQLHelper()).getOtherChannel());
	}


	@Override
	protected void initContentView() {
		mUserGridView = (DragGrid) findViewById(R.id.userGridView);
		mOtherGridView = (OtherGridView) findViewById(R.id.otherGridView);
		
		mUserAdapter = new DragAdapter(this, mUserChannelList);
		mUserGridView.setAdapter(mUserAdapter);
		mOtherAdapter = new OtherAdapter(this, mOtherChannelList);
		mOtherGridView.setAdapter(this.mOtherAdapter);
		// 设置GRIDVIEW的ITEM的点击监听
		mOtherGridView.setOnItemClickListener(this);
		mUserGridView.setOnItemClickListener(this);
	}

	@Override
	protected void initTitleView() {
		addTitleLeftBackView();
		addTitleMiddleTextView(R.string.drawer_btn_rss);
	}

	/** GRIDVIEW对应的ITEM点击监听接口 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
		// 如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if (mIsMove) {
			return;
		}
		switch (parent.getId()) {
			case R.id.userGridView:
				// position为 0的不可以进行任何操作
				if (position != 0) {
					final ImageView moveImageView = getView(view);
					if (moveImageView != null) {
						TextView newTextView = (TextView) view.findViewById(R.id.text_item);
						final int[] startLocation = new int[2];
						newTextView.getLocationInWindow(startLocation);
						final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);// 获取点击的频道内容
						mOtherAdapter.setVisible(false);
						// 添加到最后一个
						mOtherAdapter.addItem(channel);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								try {
									int[] endLocation = new int[2];
									// 获取终点的坐标
									mOtherGridView.getChildAt(mOtherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
									MoveAnim(moveImageView, startLocation, endLocation, channel, mUserGridView);
									mUserAdapter.setRemove(position);
								} catch (Exception localException) {
								}
							}
						}, 50L);
					}
				}
				break;
			case R.id.otherGridView:
				final ImageView moveImageView = getView(view);
				if (moveImageView != null) {
					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
					mUserAdapter.setVisible(false);
					// 添加到最后一个
					mUserAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								// 获取终点的坐标
								mUserGridView.getChildAt(mUserGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
								MoveAnim(moveImageView, startLocation, endLocation, channel, mOtherGridView);
								mOtherAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 点击ITEM移动动画
	 * 
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final ChannelItem moveChannel, final GridView clickGridView) {
		int[] initLocation = new int[2];
		// 获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		// 得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		// 创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(startLocation[0], endLocation[0], startLocation[1], endLocation[1]);
		moveAnimation.setDuration(300L);// 动画时间
		// 动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				mIsMove = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					mOtherAdapter.setVisible(true);
					mOtherAdapter.notifyDataSetChanged();
					mUserAdapter.remove();
				} else {
					mUserAdapter.setVisible(true);
					mUserAdapter.notifyDataSetChanged();
					mOtherAdapter.remove();
				}
				mIsMove = false;
			}
		});
	}

	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 * 
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}

	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}

	/**
	 * 获取点击的Item的对应View，
	 * 
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}

	/** 退出时候保存选择后数据库的设置 */
	private void saveChannel() {
		ChannelMgr.getManage(App.getSQLHelper()).deleteAllChannel();
		ChannelMgr.getManage(App.getSQLHelper()).saveUserChannel(mUserAdapter.getChannnelLst());
		ChannelMgr.getManage(App.getSQLHelper()).saveOtherChannel(mOtherAdapter.getChannnelLst());
	}

	@Override
	public void finish() {
		super.finish();
		
		saveChannel();
	}
}
