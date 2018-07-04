package com.ld.translation.library.http;

import okhttp3.Request;
import okhttp3.Response;

public class HttpGetParams extends AbstractHttpParams{

	@Override
	protected Response send(Request request, String baseUrl) throws Exception {
		return client.newCall(request).execute();
	}
}
