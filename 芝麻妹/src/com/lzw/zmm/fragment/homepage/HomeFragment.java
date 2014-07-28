package com.lzw.zmm.fragment.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.util.res.ResLoader;
import com.lzw.zmm.view.FlowRadioGroup;

public class HomeFragment extends BaseFragment {

	private View mHeadView;
	private View mLayoutFilterDetails;
	
	public HomeFragment() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setFragmentContentView(R.layout.home_fragment);
		
		startActivity(new Intent(getActivity(), ChannelActivity.class));
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
		FlowRadioGroup rgClassify = (FlowRadioGroup) findViewById(R.id.home_headview_classify_rg);
		rgClassify.addView(getRadioButton("全部"));
		rgClassify.addView(getRadioButton("护肤"));
		rgClassify.addView(getRadioButton("彩妆"));
		rgClassify.addView(getRadioButton("香水"));
		rgClassify.addView(getRadioButton("身体护理"));
		rgClassify.addView(getRadioButton("套装礼盒"));
		rgClassify.addView(getRadioButton("美妆工具"));
		rgClassify.check(0);
		
		// 功效筛选
		FlowRadioGroup rgEffect = (FlowRadioGroup) findViewById(R.id.home_headview_effect_rg);
		rgEffect.addView(getRadioButton("全部"));
		rgEffect.addView(getRadioButton("多功效"));
		rgEffect.addView(getRadioButton("美白"));
		rgEffect.addView(getRadioButton("抗皱"));
		rgEffect.addView(getRadioButton("紧致"));
		rgEffect.addView(getRadioButton("收敛毛孔"));
		rgEffect.addView(getRadioButton("防晒"));
		rgEffect.addView(getRadioButton("去黑眼圈"));
		rgEffect.addView(getRadioButton("祛痘"));
		rgEffect.addView(getRadioButton("保湿补水"));
		rgEffect.check(0);
		
		// 价格筛选
		FlowRadioGroup rgPrice = (FlowRadioGroup) findViewById(R.id.home_headview_price_rg);
		rgPrice.addView(getRadioButton("全部"));
		rgPrice.addView(getRadioButton("0-49元"));
		rgPrice.addView(getRadioButton("50-99元"));
		rgPrice.addView(getRadioButton("100-199元"));
		rgPrice.addView(getRadioButton("200-299元"));
		rgPrice.addView(getRadioButton("300元以上"));
		rgPrice.check(0);
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
