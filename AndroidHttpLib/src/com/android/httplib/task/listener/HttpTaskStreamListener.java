package com.android.httplib.task.listener;

import java.io.InputStream;

public abstract class HttpTaskStreamListener extends HttpTaskListener{

	public abstract Object onTaskResponse(InputStream inputStream);
}
