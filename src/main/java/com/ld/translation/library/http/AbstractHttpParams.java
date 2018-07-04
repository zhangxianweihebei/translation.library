package com.ld.translation.library.http;

import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class AbstractHttpParams implements HttpParams{
	protected final FormBody.Builder params = new FormBody.Builder();

	protected final OkHttpClient client = new OkHttpClient.Builder().build();

	@Override
	public FormBody.Builder put(String key, String value){
		params.add(key, value);
		return params;
	}

	@Override
	public String send2String(String baseUrl) throws Exception {
		Request request = new Request.Builder()
				.post(params.build())
				.url(baseUrl)
				.build();
		Response response = send(request, baseUrl);
		return response.body().string();
	}

	@Override
	public InputStream send2InputStream(String baseUrl) throws Exception {
		Request request = new Request.Builder()
				.post(params.build())
				.url(baseUrl)
				.build();
		Response response = send(request, baseUrl);
		return response.body().byteStream();
	}

	abstract protected Response send(Request httpClient, String baseUrl) throws Exception ;
}
