package com.android.httplib.task;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import com.android.httplib.LogHttp;
import com.android.httplib.exception.RequestSetParamsException;
import com.android.httplib.params.NameByteValuePair;
import com.android.httplib.params.NameFileValuePair;

public class HttpTaskRequest {

	private static final String TAG = HttpTaskRequest.class.getSimpleName();

	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
	public static final int METHOD_UPLOAD = 3;

	private HttpUriRequest mUriRequest;
	// params
	private String mBaseUrl = "", mFullUrl = "";
	private int mMethod = METHOD_GET;
	private List<NameValuePair> mValueParams;
	private List<NameByteValuePair> mByteParams;
	private List<NameFileValuePair> mFileParams;

	public HttpTaskRequest(String url, int method) {

		if (url != null) {

			mBaseUrl = url;
			mFullUrl = url;
		}

		mMethod = method;

		mValueParams = new ArrayList<NameValuePair>();

		if (mMethod == METHOD_GET) {
			mUriRequest = new HttpGet();
		} else {
			mUriRequest = new HttpPost();
		}
	}

	/*
	 * new method part
	 */

	public static HttpTaskRequest newGet(String url) {

		return new HttpTaskRequest(url, METHOD_GET);
	}

	public static HttpTaskRequest newPost(String url) {

		return new HttpTaskRequest(url, METHOD_POST);
	}

	public static HttpTaskRequest newUpload(String url) {

		return new HttpTaskRequest(url, METHOD_UPLOAD);
	}

	/*
	 * params part
	 */

	public void addParam(String key, String value) {

		mValueParams.add(new BasicNameValuePair(key, value));
	}

	public void setParams(List<NameValuePair> valueParams) {

		mValueParams = valueParams;
	}

	public void addByteParam(String key, byte[] value) {

		if (mByteParams == null)
			mByteParams = new ArrayList<NameByteValuePair>();

		mByteParams.add(new NameByteValuePair(key, value));
	}

	public void addFileParam(String key, String filePath) {

		if (mFileParams == null)
			mFileParams = new ArrayList<NameFileValuePair>();

		mFileParams.add(new NameFileValuePair(key, filePath));
	}

	public void fillParamsToUriRequest() throws RequestSetParamsException {

		try {

			switch (mMethod) {
				case METHOD_GET:

					String getUrl = HttpTaskUtil.createGetUrl(mBaseUrl, mValueParams);
					((HttpGet) mUriRequest).setURI(URI.create(getUrl));
					mFullUrl = getUrl;
					break;
				case METHOD_POST:
					HttpTaskUtil.setParamsByPostRequest((HttpPost) mUriRequest, mBaseUrl, mValueParams);
					mFullUrl = HttpTaskUtil.createGetUrl(mBaseUrl, mValueParams);
					break;
				case METHOD_UPLOAD:
					HttpTaskUtil.setParamsByUploadRequest((HttpPost) mUriRequest, mBaseUrl, mValueParams, mByteParams, mFileParams);
					mFullUrl = HttpTaskUtil.createGetUrl(mBaseUrl, mValueParams);
					break;
			}

			LogHttp.d(TAG, mFullUrl.toString());

		} catch (Exception e) {
			throw new RequestSetParamsException(e.toString());
		}
	}

	/*
	 * getter
	 */

	public HttpUriRequest getUriRequest() {

		return mUriRequest;
	}

	public String getBaseUrl() {

		return mBaseUrl;
	}

	public String getFullUrl() {

		return mFullUrl;
	}

	/*
	 * status part
	 */

	public boolean isAborted() {

		return mUriRequest.isAborted();
	}

	public void abort() {

		try {

			if (mUriRequest != null && !mUriRequest.isAborted())
				mUriRequest.abort();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
