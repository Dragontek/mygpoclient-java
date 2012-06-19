package com.dragontek.mygpoclient.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
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
		
		//TODO: Should I move most or all of this to request();?
		_targetHost = new HttpHost(host);
		
		if(username != null && password != null)
		{
			getCredentialsProvider().setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));
			_localContext.setAttribute("preemptive-auth", new BasicScheme());
		}
	
	}
	
	protected HttpRequest prepareRequest(String method, String uri, HttpEntity entity)
	{
		if(method == "POST")
		{
			HttpPost post = new HttpPost(uri);
			post.setEntity(entity);
			return post;
		}
		else if(method == "PUT")
		{
			HttpPut put = new HttpPut(uri);
			put.setEntity(entity);
			return put;
		}
		else
		{
			HttpGet get = new HttpGet(uri);
			return get;
		}
	}
	
	protected Object processResponse(HttpResponse response) throws IllegalStateException, IOException
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
		String responseString = null;
		int timeoutConnection = 3000;
		int timeoutSocket = 5000;
		
		HttpRequest request = prepareRequest(method, uri, data);
		HttpParams httpParameters = new BasicHttpParams();
		//HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		//HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);		
		setParams(httpParameters);

		HttpResponse response = execute(_targetHost, request, _localContext);
		StatusLine s = response.getStatusLine();
		if(s.getStatusCode() == 200)
		{
			responseString = (String)processResponse(response);
		} else {
			responseString = s.getReasonPhrase();
			throw new HttpResponseException(s.getStatusCode(), s.toString());
		}
		return responseString;
	}
	
	
	public String GET(String uri) throws ClientProtocolException, IOException {
		System.out.println(String.format("GET: %s", uri));
		return request("GET", uri, null);
	}
	
	public String POST(String uri, HttpEntity data) throws ClientProtocolException, IOException
	{
		System.out.println(String.format("POST: %s \r\nDATA: %s", uri, data));
		return request("POST", uri, data);
	}
	public String PUT(String uri, HttpEntity data) throws ClientProtocolException, IOException
	{
		System.out.println(String.format("PUT: %s \r\nDATA: %s", uri, data));
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

	public void setProxy() throws IOException
	{
		Properties sysProperties = System.getProperties();
		sysProperties.put("proxyHost", "<Proxy IP Address>");
		sysProperties.put("proxyPort", "<Proxy Port Number>");
		System.setProperties(sysProperties);
	}
	
}