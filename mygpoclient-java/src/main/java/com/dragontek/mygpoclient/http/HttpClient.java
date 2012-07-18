package com.dragontek.mygpoclient.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import com.dragontek.mygpoclient.Global;

public class HttpClient extends DefaultHttpClient {
	
	HttpHost _targetHost;
	BasicHttpContext _localContext = new BasicHttpContext();
	
	public HttpClient()
	{
		this(null, null, Global.HOST);
	}
	public HttpClient(String host)
	{
		this(null, null, host);
	}
	public HttpClient(String username, String password)
	{
		this(username, password, Global.HOST);
	}
	public HttpClient(String username, String password, String host)
	{
		this(username, password, host, 80); // Default to http port!
	}
	
	public HttpClient(String username, String password, String host, int port)
	{
		_targetHost = new HttpHost(host, port);
		
		if(username != null && password!= null)
			getCredentialsProvider().setCredentials(new AuthScope(_targetHost.getHostName(), _targetHost.getPort()), new UsernamePasswordCredentials(username, password));
	}
	
	protected HttpRequest prepareRequest(String method, String uri, HttpEntity entity) throws UnsupportedEncodingException
	{
		
		HttpRequest request = new HttpGet(uri);
		request.addHeader("User-agent", Global.USER_AGENT);

		if(method == "POST")
		{
			request = new HttpPost(uri);
			((HttpPost)request).setEntity(entity);
		}
		else if(method == "PUT")
		{
			request = new HttpPut(uri);
			((HttpPut)request).setEntity(entity);
		}

		return request;
	}
	
	protected String processResponse(HttpResponse response) throws IllegalStateException, IOException
	{
		String result = null;			
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
			InputStream instream = entity.getContent();
			result= convertStreamToString(instream);
			instream.close();
		}
		return result;
	}
	
	protected String request(String method, String uri, HttpEntity data) throws ClientProtocolException, IOException
	{
		HttpRequest request = prepareRequest(method, uri, data);
		
		// TODO: Remove this debug stuff
		System.out.println(String.format("%s: %s", method, uri));
		if(data != null)
		{
			System.out.println("DATA:");
			data.writeTo(System.out);
			System.out.println();
		}
		for(Header h : request.getAllHeaders())
		{
			System.out.println(String.format("HEADER: %s = %s", h.getName(), h.getValue()));
		}
		for(Cookie c : this.getCookieStore().getCookies())
		{
			System.out.println(String.format("COOKIE: %s = %s", c.getName(), c.getValue()));
		}
		HttpResponse response = execute(_targetHost, request, _localContext);
		
		/*
		AuthState targetAuthState = (AuthState) _localContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
		System.out.println("Target auth state: " + targetAuthState.getState());
		System.out.println("Target auth scheme: " + targetAuthState.getAuthScheme());
		System.out.println("Target auth credentials: " + targetAuthState.getCredentials());
		*/

		StatusLine s = response.getStatusLine();
		if(s.getStatusCode() == 200)
		{
			return (String)processResponse(response);
		} else {
			throw new HttpResponseException(s.getStatusCode(), s.toString());
		}
	}
	
	
	public String GET(String uri) throws ClientProtocolException, IOException {
		return request("GET", uri, null);
	}
	
	public String POST(String uri, HttpEntity data) throws ClientProtocolException, IOException
	{
		return request("POST", uri, data);
	}
	public String PUT(String uri, HttpEntity data) throws ClientProtocolException, IOException
	{
		return request("PUT", uri, data);
	}
	
	public static String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} finally {
			try {
				is.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}