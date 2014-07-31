package com.lzw.zmm.base;

import android.view.LayoutInflater;

import com.androidex.activity.ExFragment;

/**
 * 只用于底下的Tabhost
 * 
 * @author ForGetMan
 */
abstract public class BaseFragment extends ExFragment {

	public BaseFragment() {
		super();
	}

	protected LayoutInflater getLayoutInflater() {
		return getActivity().getLayoutInflater();
	}

	abstract public String getTagName();

	abstract public int getIvResId();

	abstract public int getViewId();
}
