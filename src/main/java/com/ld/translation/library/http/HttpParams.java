package com.ld.translation.library.http;

import java.io.InputStream;

import okhttp3.FormBody;

public interface HttpParams {
	public String send2String(String baseUrl) throws Exception;
	public InputStream send2InputStream(String baseUrl) throws Exception;
	public FormBody.Builder put(String key, String value);
}
