package com.android.httplib.task.listener;

public abstract class HttpTaskByteListener extends HttpTaskListener{

	public abstract Object onTaskResponse(byte[] bytes);
}
