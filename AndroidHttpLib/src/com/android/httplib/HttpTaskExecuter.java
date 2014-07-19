package com.android.httplib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;

import com.android.httplib.task.HttpTask;
import com.android.httplib.task.listener.HttpTaskTextListener;

public class HttpTaskExecuter {

	private Map<Integer, HttpTask> mHttpTaskMap;

	@SuppressLint("UseSparseArrays")
	public HttpTaskExecuter() {
		
		mHttpTaskMap = new HashMap<Integer, HttpTask>();
	}

	public boolean executeHttpTask(int what, final HttpTask httpTask, final HttpTaskCallback callback) {
		
		if(isHttpTaskRunning(what))
			return false;
		
		httpTask.setListener(new HttpTaskTextListener() {
			
			@Override
			public void onTaskPre() {
				
				mHttpTaskMap.put(httpTask.getWhat(), httpTask);
				callback.onHttpTaskPre(httpTask.getWhat());
			}
			
			@Override
			public Object onTaskResponse(String text) {
				return callback.onHttpTaskResponse(httpTask.getWhat(), text);
			}
			
			public boolean onTaskSaveCache(Object result){
				
				return callback.onHttpTaskSaveCache(httpTask.getWhat(), result);
			}
			
			public void onTaskSuccess(Object result) {
				
				callback.onHttpTaskSuccess(httpTask.getWhat(), result);
				mHttpTaskMap.remove(httpTask.getWhat());
			}
			
			@Override
			public void onTaskFailed(int failedCode) {
				
				callback.onHttpTaskFailed(httpTask.getWhat(), failedCode);
				mHttpTaskMap.remove(httpTask.getWhat());
			}
			
			@Override
			public void onTaskAborted() {
				callback.onHttpTaskAborted(httpTask.getWhat());
			}
			
		});
		
		httpTask.execute(what);
		return true;
	}
	
	public HttpTask getHttpTask(int what){
		
		return mHttpTaskMap.get(what);
	}
	
	public void abortHttpTask(int what) {
		
		HttpTask ht = mHttpTaskMap.remove(what);
		if(ht != null)
			ht.abort();
	}

	public void abortAllHttpTask() {
		
		if (mHttpTaskMap.size() == 0)
			return;

		Collection<HttpTask> tasks = mHttpTaskMap.values();
		Iterator<HttpTask>  iterator = tasks.iterator();
		while (iterator.hasNext()) {
			
			iterator.next().abort();
		}
		
		mHttpTaskMap.clear();
	}

	public boolean isHttpTaskRunning(int what) {
		
		return mHttpTaskMap.get(what) != null ? true : false;
	}

	public boolean isEmpty() {
		
		return mHttpTaskMap.size() == 0;
	}

	public static interface HttpTaskCallback {
		
		public void onHttpTaskPre(int what);
		
		public Object onHttpTaskResponse(int what, String text);
		
		public boolean onHttpTaskSaveCache(int what, Object result);

		public void onHttpTaskSuccess(int what, Object result);

		public void onHttpTaskFailed(int what, int failedCode);

		public void onHttpTaskAborted(int what);
	}

}
