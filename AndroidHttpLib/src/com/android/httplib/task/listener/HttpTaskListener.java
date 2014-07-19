package com.android.httplib.task.listener;

public abstract class HttpTaskListener{

	public static final int TASK_SUCCESS = 0;
	public static final int TASK_FAILED_NETWORK = 1;
	public static final int TASK_FAILED_SETPARAMS = 2;
	public static final int TASK_FAILED_TIMEOUT = 3;
	public static final int TASK_FAILED_CONNECT = 4;
	public static final int TASK_FAILED_SHUTDOWN = 5;
	public static final int TASK_FAILED_SERVER_ERROR = 6;
	public static final int TASK_FAILED_CLIENT_ERROR = 7;
	
	public void onTaskPre(){
		
	}
	
	public void onTaskPre(Object cache){
		
	}
	
	public void onTaskSuccess(Object result){
		
	}

	public void onTaskFailed(int failedCode){
		
	}

	public void onTaskAborted(){
		
	}
}
