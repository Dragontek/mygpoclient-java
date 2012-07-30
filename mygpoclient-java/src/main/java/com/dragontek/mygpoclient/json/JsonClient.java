package com.dragontek.mygpoclient.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.*;

import com.dragontek.mygpoclient.http.HttpClient;
import com.google.gson.Gson;

public class JsonClient extends HttpClient {

	public JsonClient()
	{
		super();
	}
	public JsonClient(String host)
	{
		super(host);
	}
	
	public JsonClient(String username, String password, String host)
	{
		super(username, password, host);
	}

	@Override
	protected HttpRequest prepareRequest(String method, String uri, HttpEntity data) throws UnsupportedEncodingException {
		HttpRequest request = super.prepareRequest(method, uri, data);
		request.addHeader("Accept", "application/json");
		
		return request;
	}

	@Override
	protected String processResponse(HttpResponse response) throws IllegalStateException, IOException {
		String data = (String) super.processResponse(response);
		//return decode(data);
		return data; 
	};
	
	public static String encode(Object data)
	{
		Gson gson = new Gson();
		return gson.toJson(data);
	}
	
	public static <T> T decode(String data, Class<T> clazz)
	{
		Gson gson = new Gson();
		return gson.fromJson(data, clazz);
	}
}