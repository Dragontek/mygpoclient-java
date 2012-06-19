package com.dragontek.mygpoclient.json;

import java.io.IOException;
import org.apache.http.*;
import org.json.*;

import com.dragontek.mygpoclient.http.HttpClient;

public class JsonClient extends HttpClient {
	// TODO: Do I even need this class?  I'm not doing anything useful here since I can't overload methods and return different types (like you can in python).
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
	protected HttpRequest prepareRequest(String method, String uri, HttpEntity data) {
		return super.prepareRequest(method, uri, data);
	}

	@Override
	protected Object processResponse(HttpResponse response) throws IllegalStateException, IOException {
		String data = (String) super.processResponse(response);
		return data; //decode(data);
	};
	
	public static String encode(Object data)
	{
		return data.toString();
	}
	
	public static JSONObject decode(String data) throws JSONException
	{
		return new JSONObject(data);
	}
}