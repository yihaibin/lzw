package com.lzw.zmm.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.lzw.lib.base.LogMgr;
import com.lzw.zmm.R;
import com.lzw.zmm.base.BaseFragment;
import com.lzw.zmm.base.BaseFragmentActivity;
import com.lzw.zmm.fragment.barcode.BarcodeFragment;
import com.lzw.zmm.fragment.homepage.HomepageFragment;
import com.lzw.zmm.fragment.record.RecordFragment;
import com.lzw.zmm.utils.FragmentUtil;

public class FragmentTabs extends BaseFragmentActivity implements OnTabChangeListener {

	private static final String TAG = FragmentTabs.class.getSimpleName();

	private TabHost mTabHost;
	private View mTabs;

	private ArrayList<BaseFragment> mFragments;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}

	@Override
	protected void initData() {
		mFragments = new ArrayList<BaseFragment>();

		mFragments.add(new HomepageFragment());
		mFragments.add(new BarcodeFragment());
		mFragments.add(new RecordFragment());
	}

	@Override
	protected void findViews() {
		mTabHost = (TabHost) this.findViewById(R.id.main_tabhost);
		mTabs = findViewById(android.R.id.tabs);
	}

	@Override
	protected void setViewsValue() {
		mTabHost.setup();

		for (int i = 0; i < mFragments.size(); i++) {
			View v = getLayoutInflater().inflate(R.layout.main_tab_item, null);

			BaseFragment fragment = mFragments.get(i);

			TextView tv = (TextView) v.findViewById(R.id.main_tab_tv);
			String name = fragment.getTagName();
			tv.setText(name);

			mTabHost.addTab(mTabHost.newTabSpec(name).setIndicator(v).setContent(fragment.getViewId()));
		}

		mTabHost.setOnTabChangedListener(this);

		onTabChanged(mFragments.get(0).getTagName());
	}

	@Override
	public void onTabChanged(String tagName) {

		LogMgr.d(TAG, "onTabChanged = " + tagName);

		Fragment fragment = null;
		int viewID = 0;

		for (int i = 0; i < mFragments.size(); ++i) {
			BaseFragment eachFragment = mFragments.get(i);
			if (tagName.equals(eachFragment.getTagName())) {
				fragment = eachFragment;
				viewID = eachFragment.getViewId();
				break;
			}
		}

		if (fragment == null) {
			return;
		}

		FragmentUtil.startFragment(this, tagName, viewID, fragment);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mFragments.clear();
	}

}
