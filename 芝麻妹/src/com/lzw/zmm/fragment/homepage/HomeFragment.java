package com.lzw.zmm.fragment.homepage;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.androidex.util.LogMgr;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.util.res.ResLoader;
import com.lzw.zmm.view.FlowRadioGroup;

public class HomeFragment extends BaseFragment {

	private static final String TAG = HomeFragment.class.getSimpleName();
	
	private View mHeadView;
	private View mLayoutFilterDetails;
	
	private ArrayList<RadioButton> mRbsClassify = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsEffect = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsPrice = new ArrayList<RadioButton>();
	private ArrayList<RadioButton> mRbsOther = new ArrayList<RadioButton>();
	
	public HomeFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setFragmentContentView(R.layout.home_fragment);
		
//		startActivity(new Intent(getActivity(), ChannelActivity.class));
	}
	

	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_home);
	}

	@Override
	public int getViewId() {
		return R.id.main_tab_fragment_homepage;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initContentView() {
		mHeadView = findViewById(R.id.homepage_headview);
		mLayoutFilterDetails = findViewById(R.id.home_headview_layout_filter_details);
		
		/**
		 * 使用动态的方式好处:
		 * 1. 方便添加, 代码简介
		 * 2. 可以适应以后可能出现的动态改变
		 */
		// 分类筛选
		mRbsClassify.add(getRadioButton("全部"));
		mRbsClassify.add(getRadioButton("护肤"));
		mRbsClassify.add(getRadioButton("彩妆"));
		mRbsClassify.add(getRadioButton("香水"));
		mRbsClassify.add(getRadioButton("身体护理"));
		mRbsClassify.add(getRadioButton("套装礼盒"));
		mRbsClassify.add(getRadioButton("美妆工具"));
		
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
		mRbsEffect.add(getRadioButton("全部"));
		mRbsEffect.add(getRadioButton("多功效"));
		mRbsEffect.add(getRadioButton("美白"));
		mRbsEffect.add(getRadioButton("抗皱"));
		mRbsEffect.add(getRadioButton("紧致"));
		mRbsEffect.add(getRadioButton("收敛毛孔"));
		mRbsEffect.add(getRadioButton("防晒"));
		mRbsEffect.add(getRadioButton("去黑眼圈"));
		mRbsEffect.add(getRadioButton("祛痘"));
		mRbsEffect.add(getRadioButton("保湿补水"));
		
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
		mRbsPrice.add(getRadioButton("全部"));
		mRbsPrice.add(getRadioButton("0-49元"));
		mRbsPrice.add(getRadioButton("50-99元"));
		mRbsPrice.add(getRadioButton("100-199元"));
		mRbsPrice.add(getRadioButton("200-299元"));
		mRbsPrice.add(getRadioButton("300元以上"));
		
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
		mRbsOther.add(getRadioButton("全部"));
		mRbsOther.add(getRadioButton("限时特卖"));
		
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
	}

	@Override
	protected void initTitleView() {
		
	}
	
	private RadioButton getRadioButton(String text) {
		RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.home_headview_radiobtn, null);
		radioButton.setText(text);
		return radioButton;
	}
}
