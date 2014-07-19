package com.android.httplib.task.listener;

public abstract class HttpTaskTextListener extends HttpTaskListener{

	public abstract Object onTaskResponse(String text);
	
	public boolean onTaskSaveCache(Object result){
		
		return false;
	}
}
