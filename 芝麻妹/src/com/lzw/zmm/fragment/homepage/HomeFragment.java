package com.lzw.zmm.fragment.homepage;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.androidex.util.LogMgr;
import com.lzw.zmm.App;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.bean.ChannelItem;
import com.lzw.zmm.bean.ChannelMgr;
import com.lzw.zmm.bean.Good;
import com.lzw.zmm.util.res.ResLoader;
import com.lzw.zmm.view.FlowRadioGroup;

public class HomeFragment extends BaseFragment {

	private static final String TAG = HomeFragment.class.getSimpleName();
	
	public static final int KRequestCodeAddChannels = 0;
	
	private ListView mLv;
	
	private View mHeadView;
	private View mHeadViewTitle;
	
	private View mLayoutFilterDetails;
	private View mLayoutFilterNotShow;
	private View mBtnConfirm;
	
	private RadioGroup mRgNavBar;
	private ArrayList<RadioButton> mRbsNavBar = new ArrayList<RadioButton>();
	
	private ArrayList<RadioButton> mRbsClassify = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsEffect = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsPrice = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsOther = new ArrayList<RadioButton>();
	
	private ArrayList<RadioButton> mRbsGoodsSort = new ArrayList<RadioButton>();
	
	private ArrayList<Good> mGoods;
	private HomeAdapter mAdapter;
	
	private boolean mIsShowFilter = false;
	
	public HomeFragment() {
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setFragmentContentView(R.layout.home_fragment);
	}
	

	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_home);
	}

	@Override
	public int getIvResId() {
		return R.drawable.tab_icon_home_selector;
	}
	
	@Override
	public int getViewId() {
		return R.id.main_tab_fragment_homepage;
	}

	@Override
	protected void initData() {
		List<ChannelItem> channels = ChannelMgr.getManage(App.getSQLHelper()).getUserChannel();
		for (int i = 0; i < channels.size(); ++i) {
			mRbsNavBar.add(getNavBarRadioButton(channels.get(i).getName()));
		}
		
		// TODO: 测试数据
		mGoods = new ArrayList<Good>();
		for (int i = 0; i < 20; ++i) {
			mGoods.add(new Good());
		}
	}

	@Override
	protected void initContentView() {
		mLv = (ListView) findViewById(R.id.home_lv);
		
		initHeadView();
		mLv.addHeaderView(mHeadView);
		
		mBtnConfirm = findViewById(R.id.home_headview_btn_confirm);
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideFilterView();
				// 重新从网络获取数据
				
			}
		});
		mLayoutFilterNotShow = findViewById(R.id.home_headview_layout_filter_not_show);
		
		initFilterView();
		
		mAdapter = new HomeAdapter(mGoods);
		mLv.setAdapter(mAdapter);
		
		hideFilterView();
		
		ImageView ivTop = (ImageView) findViewById(R.id.ivToTop);
		ivTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!mAdapter.isEmpty()) {
					mLv.setSelectionFromTop(0, 0);
				}
			}
		});
	}

	@Override
	protected void initTitleView() {
		getTitleView().setVisibility(View.GONE);
	}
	
	private void initHeadView() {
		mHeadView = getLayoutInflater().inflate(R.layout.home_headview, null);
		mLayoutFilterDetails = mHeadView.findViewById(R.id.home_headview_layout_filter_details);
		mHeadViewTitle = mHeadView.findViewById(R.id.home_headview_layout_title);
		mHeadViewTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mIsShowFilter) {
					mIsShowFilter = false;
					hideFilterView();
				} else {
					mIsShowFilter = true;
					showFilterView();
				}
			}
		});
		
		// 默认/销量
		RadioGroup headViewRg = (RadioGroup) mHeadView.findViewById(R.id.home_headview_rg);
		mRbsGoodsSort.add((RadioButton) mHeadView.findViewById(R.id.home_headview_radiobtn_default));
		mRbsGoodsSort.add((RadioButton) mHeadView.findViewById(R.id.home_headview_radiobtn_sales));
		headViewRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
			}
		});
		headViewRg.check(mRbsGoodsSort.get(0).getId());
	}
	
	private void initFilterView() {
		/**
		 * 使用动态的方式好处:
		 * 1. 方便添加, 代码简介
		 * 2. 可以适应以后可能出现的动态改变
		 */
		// 分类筛选
		mRbsClassify.add(getHeadViewRadioButton("全部"));
		mRbsClassify.add(getHeadViewRadioButton("护肤"));
		mRbsClassify.add(getHeadViewRadioButton("彩妆"));
		mRbsClassify.add(getHeadViewRadioButton("香水"));
		mRbsClassify.add(getHeadViewRadioButton("身体护理"));
		mRbsClassify.add(getHeadViewRadioButton("套装礼盒"));
		mRbsClassify.add(getHeadViewRadioButton("美妆工具"));
		
		FlowRadioGroup rgClassify = (FlowRadioGroup) findViewById(R.id.home_headview_classify_rg);
		for (int i = 0; i < mRbsClassify.size(); ++i) {
			rgClassify.addView(mRbsClassify.get(i));
		}
		rgClassify.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				LogMgr.d(TAG, "rgClassify position = " + checkedId);
			}
		});
		rgClassify.check(mRbsClassify.get(0).getId());
		
		// 功效筛选
		mRbsEffect.add(getHeadViewRadioButton("全部"));
		mRbsEffect.add(getHeadViewRadioButton("多功效"));
		mRbsEffect.add(getHeadViewRadioButton("美白"));
		mRbsEffect.add(getHeadViewRadioButton("抗皱"));
		mRbsEffect.add(getHeadViewRadioButton("紧致"));
		mRbsEffect.add(getHeadViewRadioButton("收敛毛孔"));
		mRbsEffect.add(getHeadViewRadioButton("防晒"));
		mRbsEffect.add(getHeadViewRadioButton("去黑眼圈"));
		mRbsEffect.add(getHeadViewRadioButton("祛痘"));
		mRbsEffect.add(getHeadViewRadioButton("保湿补水"));
		
		FlowRadioGroup rgEffect = (FlowRadioGroup) findViewById(R.id.home_headview_effect_rg);
		for (int i = 0; i < mRbsEffect.size(); ++i) {
			rgEffect.addView(mRbsEffect.get(i));
		}
		rgEffect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				LogMgr.d(TAG, "rgEffect position = " + checkedId);
			}
		});
		rgEffect.check(mRbsEffect.get(0).getId());
		
		// 价格筛选
		mRbsPrice.add(getHeadViewRadioButton("全部"));
		mRbsPrice.add(getHeadViewRadioButton("0-49元"));
		mRbsPrice.add(getHeadViewRadioButton("50-99元"));
		mRbsPrice.add(getHeadViewRadioButton("100-199元"));
		mRbsPrice.add(getHeadViewRadioButton("200-299元"));
		mRbsPrice.add(getHeadViewRadioButton("300元以上"));
		
		FlowRadioGroup rgPrice = (FlowRadioGroup) findViewById(R.id.home_headview_price_rg);
		for (int i = 0; i < mRbsPrice.size(); ++i) {
			rgPrice.addView(mRbsPrice.get(i));
		}
		rgPrice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				LogMgr.d(TAG, "rgPrice position = " + checkedId);
			}
		});
		rgPrice.check(mRbsPrice.get(0).getId());
		
		// 其他筛选
		mRbsOther.add(getHeadViewRadioButton("全部"));
		mRbsOther.add(getHeadViewRadioButton("限时特卖"));
		
		FlowRadioGroup rgOther = (FlowRadioGroup) findViewById(R.id.home_headview_other_rg);
		for (int i = 0; i < mRbsOther.size(); ++i) {
			rgOther.addView(mRbsOther.get(i));
		}
		rgOther.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int id = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(id);
				LogMgr.d(TAG, "rgOther position = " + rb.getText().toString());
			}
		});
		rgOther.check(mRbsOther.get(0).getId());
		
		
		// nav bar添加数据
		mRgNavBar = (RadioGroup) findViewById(R.id.home_nav_bar_rg);
		for (int i = 0; i < mRbsNavBar.size(); ++i) {
			mRgNavBar.addView(mRbsNavBar.get(i));
		}
		mRgNavBar.check(mRbsNavBar.get(0).getId());
		
		
		// 添加频道
		View ivAddChanel = findViewById(R.id.home_nav_bar_iv_add);
		ivAddChanel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().startActivityFromFragment(HomeFragment.this, new Intent(getActivity(), ChannelActivity.class), KRequestCodeAddChannels);
			}
		});
	}
	
	private RadioButton getNavBarRadioButton(String text) {
		RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.home_nav_radiobtn, null);
		radioButton.setText(text);
		return radioButton;
	}
	
	
	private RadioButton getHeadViewRadioButton(String text) {
		RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.home_headview_radiobtn, null);
		radioButton.setText(text);
		return radioButton;
	}
	
	/**
	 * 从设置频道页面回来. 需要重新加载一次
	 */
	public void updateChannel() {
		mRbsNavBar.clear();
		List<ChannelItem> channels = ChannelMgr.getManage(App.getSQLHelper()).getUserChannel();
		for (int i = 0; i < channels.size(); ++i) {
			mRbsNavBar.add(getNavBarRadioButton(channels.get(i).getName()));
		}
		
		mRgNavBar.removeAllViews();
		for (int i = 0; i < mRbsNavBar.size(); ++i) {
			mRgNavBar.addView(mRbsNavBar.get(i));
		}
		mRgNavBar.check(mRbsNavBar.get(0).getId());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateChannel();
	}
	
	
	private void showFilterView() {
		mLayoutFilterDetails.setVisibility(View.VISIBLE);
		mLayoutFilterNotShow.setVisibility(View.GONE);
		mBtnConfirm.setVisibility(View.VISIBLE);
	}
	
	private void hideFilterView() {
		mLayoutFilterDetails.setVisibility(View.GONE);
		mLayoutFilterNotShow.setVisibility(View.VISIBLE);
		mBtnConfirm.setVisibility(View.GONE);
	}
}
