package com.dragontek.mygpoclient.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.*;
import org.apache.http.entity.StringEntity;

import com.dragontek.mygpoclient.http.HttpClient;
import com.google.gson.Gson;

public class JsonClient extends HttpClient {
	// TODO: Do I even need this class?  I'm not doing anything useful here since I can't overload methods and 
	// return different types (like you can in python).
	// Perhaps with Generics, but then the user would have to pass in the type they want
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
		StringEntity entity = new StringEntity("");
		if(data instanceof StringEntity)
		{
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
		}
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