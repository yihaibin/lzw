package com.lzw.zmm.base;

import com.lzw.lib.ex.FragmentEx;
import com.lzw.zmm.dialog.DialogMgr;

/**
 * 只用于底下的Tabhost
 * @author ForGetMan
 */
abstract public class BaseFragment extends FragmentEx  {

	private DialogMgr mDialogMgr;

	public BaseFragment() {
		super();
	}

	abstract public String getTagName();

	abstract public int getViewId();

	protected DialogMgr getDialogMgr() {
		if (mDialogMgr == null) {
			mDialogMgr = new DialogMgr(getActivity());
		}
		return mDialogMgr;
	}
}
