package com.lzw.zmm.fragment.homepage;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import com.androidex.adapter.CustomizeAdapter;
import com.lzw.zmm.R;
import com.lzw.zmm.bean.Good;

public class HomeAdapter extends CustomizeAdapter<Good> {
	
	public HomeAdapter(List<Good> data) {
		super(data);
	}

	@Override
	protected View createConvertView(int position, ViewGroup parent) {
		View convertView = getLayoutInflater().inflate(R.layout.goods_item, null);
		
		HomeItemCache itemCache = new HomeItemCache(convertView);

		
		convertView.setTag(itemCache);
		return convertView;
	}

	@Override
	protected void freshConvertView(int position, View convertView, ViewGroup parent) {
		HomeItemCache itemCache = (HomeItemCache) convertView.getTag();
		
	}

}
