package com.lzw.zmm.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

public class DialogMgr {

	// private static final String TAG = DialogUtil.class.getSimpleName();

	private Context mContext;
	private Dialog mCurrnetDialog;

	public static DialogMgr mSelf = null;
	
	public OnDialogClickListener mListener;

	public DialogMgr(Context context) {
		mContext = context;
	}

	public void setOnClickListener(OnDialogClickListener listener) {
		mListener = listener;
	}
	
	public void dismissCurDialog() {
		if (mCurrnetDialog != null) {
			mCurrnetDialog.dismiss();
		}

		mCurrnetDialog = null;
	}

	public void showAlertDialog(String title, String content, OnCancelListener listener) {
		dismissCurDialog();

		Builder builder = new Builder(mContext);
		builder.setTitle(title);
		builder.setMessage(content);

		mCurrnetDialog = builder.create();
		mCurrnetDialog.setOnCancelListener(listener);
		mCurrnetDialog.setCanceledOnTouchOutside(false);
		mCurrnetDialog.show();
	}
}