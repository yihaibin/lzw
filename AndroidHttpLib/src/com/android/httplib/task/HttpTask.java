package com.android.httplib.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;

import android.os.Environment;

import com.android.httplib.IOUtil;
import com.android.httplib.LogHttp;
import com.android.httplib.exception.ClientErrorException;
import com.android.httplib.exception.HttpClientShutdownException;
import com.android.httplib.exception.NetworkDisableException;
import com.android.httplib.exception.RequestSetParamsException;
import com.android.httplib.exception.ServerErrorException;
import com.android.httplib.task.HttpTaskClient.InputStreamCallback;
import com.android.httplib.task.listener.HttpTaskByteListener;
import com.android.httplib.task.listener.HttpTaskListener;
import com.android.httplib.task.listener.HttpTaskStreamListener;
import com.android.httplib.task.listener.HttpTaskTextListener;

public class HttpTask extends AsyncHttpTask<Void, Void, Object> {

	private static final String TAG = HttpTask.class.getSimpleName();

	protected static HttpTaskClient mHttpTaskClient = HttpTaskClient.newThreadSafeHttpClient(10 * 1000);// 超时时间
																										// 10s;
	protected static File mCacheFileDir = new File(Environment.getExternalStorageDirectory(), "android" + File.separator + "urlCache" + File.separator);

	private HttpTaskRequest mHttpTaskRequest;
	private HttpTaskListener mHttpTaskLisn;
	private int mTaskStatus = HttpTaskListener.TASK_SUCCESS;
	private int mWhat;
	private boolean mOnlyLoadTextCache;
	private String mCacheKey;

	private HttpTask(HttpTaskRequest httpTaskRequest) {

		mHttpTaskRequest = httpTaskRequest;
	}

	public static HttpTask newGet(String url) {

		return new HttpTask(HttpTaskRequest.newGet(url));
	}

	public static HttpTask newPost(String url) {

		return new HttpTask(HttpTaskRequest.newPost(url));
	}

	public static HttpTask newUpload(String url) {

		return new HttpTask(HttpTaskRequest.newUpload(url));
	}

	/*
	 * setter
	 */

	public void setListener(HttpTaskListener lisn) {

		mHttpTaskLisn = lisn;
	}

	public void setOnlyLoadTextCache(boolean onlyLoadTextCache) {

		mOnlyLoadTextCache = onlyLoadTextCache;
	}

	public boolean isOnlyLoadTextCache() {

		return mOnlyLoadTextCache;
	}

	public void setCacheKey(String cacheKey) {

		mCacheKey = cacheKey;
	}

	/*
	 * add params part
	 */

	public void addParam(String key, String value) {

		mHttpTaskRequest.addParam(key, value);
	}

	public void setParams(List<NameValuePair> valueParams) {

		mHttpTaskRequest.setParams(valueParams);
	}

	public void addByteParam(String key, byte[] value) {

		mHttpTaskRequest.addByteParam(key, value);
	}

	public void addFileParam(String key, String filePath) {

		mHttpTaskRequest.addFileParam(key, filePath);
	}

	/*
	 * status part
	 */

	public final HttpTask execute() {

		if (mOnlyLoadTextCache) {

			super.executeLocal();
		} else {

			super.executeRemote();
		}

		return this;
	}

	public final HttpTask execute(int what) {

		mWhat = what;
		return this.execute();
	}

	public final int getWhat() {

		return mWhat;
	}

	public final void abort() {

		if (isCancelled())
			return;

		cancel(false);
		mHttpTaskRequest.abort();
	}

	public final boolean isAbort() {

		return isCancelled();
	}

	public final boolean isRunning() {

		return getStatus() == Status.RUNNING;
	}

	public final boolean isFinished() {

		return getStatus() == Status.FINISHED;
	}

	/*
	 * callback methods part
	 */
	protected final void onPreExecute() {

		if (mHttpTaskLisn != null)
			mHttpTaskLisn.onTaskPre();
	}

	@Override
	protected final Object doInBackground(Void... params) {

		try {

			return executeHttpUriRequest();

		} catch (Throwable e) {

			exception2FailedCode(e);
			return null;
		}
	}

	@Override
	protected final void onPostExecute(Object t) {

		if (mTaskStatus == HttpTaskListener.TASK_SUCCESS) {

			if (mHttpTaskLisn != null)
				mHttpTaskLisn.onTaskSuccess(t);

		} else {

			if (mHttpTaskLisn != null)
				mHttpTaskLisn.onTaskFailed(mTaskStatus);
		}
	}

	@Override
	protected final void onCancelled() {

		if (mHttpTaskLisn != null)
			mHttpTaskLisn.onTaskAborted();
	}

	/*
	 * help method part
	 */

	private Object executeHttpUriRequest() throws ClientProtocolException, IOException, HttpClientShutdownException, RequestSetParamsException, ServerErrorException,
			ClientErrorException {

		mHttpTaskRequest.fillParamsToUriRequest();
		if (mHttpTaskLisn instanceof HttpTaskTextListener) {

			String text = null;
			Object result = null;
			if (mOnlyLoadTextCache) {

				text = loadTextCache(mCacheKey == null ? mHttpTaskRequest.getFullUrl() : mCacheKey);
				result = ((HttpTaskTextListener) mHttpTaskLisn).onTaskResponse(text);
			} else {

				text = mHttpTaskClient.executeText(mHttpTaskRequest.getUriRequest());
				result = ((HttpTaskTextListener) mHttpTaskLisn).onTaskResponse(text);
				boolean needCache = ((HttpTaskTextListener) mHttpTaskLisn).onTaskSaveCache(result);
				if (needCache)
					saveTextCache(mCacheKey == null ? mHttpTaskRequest.getFullUrl() : mCacheKey, text);
			}

			return result;

		} else if (mHttpTaskLisn instanceof HttpTaskStreamListener) {

			HttpTaskInputStreamCallback callback = new HttpTaskInputStreamCallback((HttpTaskStreamListener) mHttpTaskLisn);
			mHttpTaskClient.executeInputStream(mHttpTaskRequest.getUriRequest(), callback);
			return callback.getResultObj();

		} else if (mHttpTaskLisn instanceof HttpTaskByteListener) {

			byte[] bytes = mHttpTaskClient.executeByteArray(mHttpTaskRequest.getUriRequest());
			return ((HttpTaskByteListener) mHttpTaskLisn).onTaskResponse(bytes);

		} else {

			mHttpTaskClient.executeHttpResponse(mHttpTaskRequest.getUriRequest());
			return null;
		}
	}

	private String loadTextCache(String cacheKey) {

		String text = null;
		try {

			if (!mCacheFileDir.exists())
				mCacheFileDir.mkdirs();

			text = (String) IOUtil.readObj(mCacheFileDir, String.valueOf(cacheKey.hashCode()));

		} catch (Exception e) {
			LogHttp.e(TAG, e);
		}

		return text == null ? "" : text;
	}

	private void saveTextCache(String cachekey, String text) {

		try {

			if (!mCacheFileDir.exists())
				mCacheFileDir.mkdirs();

			IOUtil.writeObj(text, mCacheFileDir, String.valueOf(cachekey.hashCode()));

		} catch (Exception e) {
			LogHttp.e(TAG, e);
		}
	}

	private void exception2FailedCode(Throwable e) {

		if (e instanceof NetworkDisableException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_NETWORK;
		} else if (e instanceof RequestSetParamsException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_SETPARAMS;
		} else if (e instanceof IllegalStateException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_SHUTDOWN;
		} else if (e instanceof SocketTimeoutException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_TIMEOUT;
		} else if (e instanceof ConnectTimeoutException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_TIMEOUT;
		} else if (e instanceof ServerErrorException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_SERVER_ERROR;
		} else if (e instanceof ClientErrorException) {

			mTaskStatus = HttpTaskListener.TASK_FAILED_CLIENT_ERROR;
		} else {

			mTaskStatus = HttpTaskListener.TASK_FAILED_CONNECT;
		}

		LogHttp.e(TAG, e);
	}

	/*
	 * static method part
	 */

	public static void setCacheDir(File cacheDir) {

		if (cacheDir != null)
			mCacheFileDir = cacheDir;
	}

	public static final void shutdown() {

		if (mHttpTaskClient != null) {

			mHttpTaskClient.shutdown();
			mHttpTaskClient = null;
		}
	}

	/*
	 * httpclient inputstream callback
	 */
	private class HttpTaskInputStreamCallback implements InputStreamCallback {

		Object resultObj;
		HttpTaskStreamListener streamLisn;

		public HttpTaskInputStreamCallback(HttpTaskStreamListener lisn) {

			this.streamLisn = lisn;
		}

		@Override
		public void onInputStream(InputStream input) {

			resultObj = streamLisn.onTaskResponse(input);
			streamLisn = null;
		}

		public Object getResultObj() {

			return resultObj;
		}
	}
}