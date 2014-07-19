package com.lzw.lib.ex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.httplib.HttpTaskExecuter;
import com.android.httplib.HttpTaskExecuter.HttpTaskCallback;
import com.android.httplib.task.HttpTask;
import com.lzw.lib.utils.ToastUtil;

abstract public class FragmentEx extends Fragment implements HttpTaskCallback {

	private HttpTaskExecuter mHttpTaskExecuter;

	private View mBaseView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mBaseView = createView(inflater, container);
		init();
		return mBaseView;
	}

	private void init() {
		initData();
		findViews();
		setViewsValue();
	}

	abstract protected View createView(LayoutInflater inflater, ViewGroup container);

	abstract protected void initData();

	abstract protected void findViews();

	abstract protected void setViewsValue();

	protected LayoutInflater getLayoutInflater() {
		return getActivity().getLayoutInflater();
	}

	public View getBaseView() {
		return mBaseView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		abortAllHttpTask();
	}

	protected void showToask(String content) {
		ToastUtil.makeToast(content);
	}

	protected void showToask(int resId) {
		ToastUtil.makeToast(resId);
	}

	/**
	 * http task part
	 */

	public boolean executeHttpTask(int what, HttpTask httpTask) {

		if (isRemoving())
			return false;

		if (mHttpTaskExecuter == null)
			mHttpTaskExecuter = new HttpTaskExecuter();

		return mHttpTaskExecuter.executeHttpTask(what, httpTask, this);
	}

	public boolean isHttpTaskRunning(int what) {

		if (mHttpTaskExecuter == null) {

			return false;
		} else {

			return mHttpTaskExecuter.isHttpTaskRunning(what);
		}
	}

	public void abortHttpTask(int what) {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortHttpTask(what);
	}

	public void abortAllHttpTask() {

		if (mHttpTaskExecuter != null)
			mHttpTaskExecuter.abortAllHttpTask();
	}

	/**
	 * http task callback part
	 */

	@Override
	public void onHttpTaskPre(int what) {
	}

	@Override
	public Object onHttpTaskResponse(int what, String text) {

		return null;
	}

	@Override
	public boolean onHttpTaskSaveCache(int what, Object result) {

		return false;
	}

	@Override
	public void onHttpTaskSuccess(int what, Object result) {
	}

	@Override
	public void onHttpTaskFailed(int what, int failedCode) {
	}

	@Override
	public void onHttpTaskAborted(int what) {
	}

	protected View findViewById(int id) {
		return mBaseView.findViewById(id);
	}
}
