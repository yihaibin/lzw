package com.android.httplib.task;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;

import com.android.httplib.params.NameByteValuePair;
import com.android.httplib.params.NameFileValuePair;

class HttpTaskUtil {

	public static void setParamsByGetRequest(HttpGet request, String url, List<NameValuePair> params) {

		request.setURI(URI.create(createGetUrl(url, params)));
	}

	public static String createGetUrl(String url, List<NameValuePair> params) {

		if (params == null || params.size() == 0)
			return url;

		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append('?');
		NameValuePair pair = null;
		for (int i = 0; i < params.size(); i++) {

			pair = params.get(i);
			if (i != 0) {
				sb.append('&');
			}
			sb.append(pair.getName());
			sb.append('=');
			sb.append(pair.getValue());
		}

		return sb.toString();
	}

	public static void setParamsByPostRequest(HttpPost httpPost, String url, List<NameValuePair> params) throws IOException {

		httpPost.setURI(URI.create(url));
		httpPost.setHeader("Charset", HTTP.UTF_8);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		if (params != null && params.size() > 0)
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	}

	public static void setParamsByUploadRequest(HttpPost httpPost, String url, List<NameValuePair> nameParams, List<NameByteValuePair> byteParams,
			List<NameFileValuePair> fileParams) throws IOException {

		httpPost.setURI(URI.create(url));
		MultipartEntity me = new MultipartEntity();
		setStringBodyToMultipartEntity(me, nameParams);
		setByteArrayBodyToMultipartEntity(me, byteParams);
		setFileBodyToMultipartEntity(me, fileParams);
		httpPost.setEntity(me);
	}

	private static void setStringBodyToMultipartEntity(MultipartEntity me, List<NameValuePair> params) throws UnsupportedEncodingException {

		if (params == null || params.size() == 0)
			return;

		NameValuePair nvp = null;
		for (int i = 0; i < params.size(); i++) {
			nvp = params.get(i);
			me.addPart(nvp.getName(), new StringBody(nvp.getValue(), Charset.forName("UTF-8")));
		}
	}

	private static void setByteArrayBodyToMultipartEntity(MultipartEntity me, List<NameByteValuePair> params) throws UnsupportedEncodingException {

		if (params == null || params.size() == 0)
			return;

		NameByteValuePair nbvp = null;
		for (int i = 0; i < params.size(); i++) {
			nbvp = params.get(i);
			me.addPart(nbvp.getName(), new ByteArrayBody(nbvp.getData(), nbvp.getName()));
		}
	}

	private static void setFileBodyToMultipartEntity(MultipartEntity me, List<NameFileValuePair> params) throws UnsupportedEncodingException {

		if (params == null || params.size() == 0)
			return;

		NameFileValuePair nuvp = null;
		for (int i = 0; i < params.size(); i++) {
			nuvp = params.get(i);
			me.addPart(nuvp.getName(), new FileBody(new File(nuvp.getData())));
		}
	}
}
