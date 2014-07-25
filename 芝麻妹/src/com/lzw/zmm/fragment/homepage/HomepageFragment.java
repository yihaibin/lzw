package com.lzw.zmm.fragment.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzw.lib.util.res.ResLoader;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;

public class HomepageFragment extends BaseFragment {

	public HomepageFragment() {
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.homepage_fragment, container, false);
	}

	@Override
	public String getTagName() {
		return ResLoader.getString(R.string.tab_name_homepage);
	}

	@Override
	public int getViewId() {
		return R.id.main_tab_fragment_homepage;
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void findViews() {
	}

	@Override
	protected void setViewsValue() {

	}
}
