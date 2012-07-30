package com.dragontek.mygpoclient.simple;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.dragontek.mygpoclient.Global;
import com.dragontek.mygpoclient.Locator;
import com.dragontek.mygpoclient.http.HttpClient;
import com.dragontek.mygpoclient.json.JsonClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Client for the gpodder.net Simple API
 * <p>
 * This is the API client implementation that provides a
 * java interface to the gpodder.net Simple API.
 * 
 * @author jmondragon
 *
 */
public class SimpleClient
{
	protected Locator _locator;
	protected JsonClient _client;
	protected String _authToken;
	protected Gson _gson;
	public static String FORMAT = "json";

	
	public SimpleClient(String username)
	{
		this(username, null, Global.HOST);
	}
	
	
	/**
	 * Creates a new Simple API client

        Username and password must be specified and are
        the user's login data for the webservice.

        The parameter host is optional and defaults to
        the main webservice.

        The parameter client_class is optional and should
        not need to be changed in normal use cases. If it
        is changed, it should provide the same interface
        as the json.JsonClient class in mygpoclient.
        
	 * @param username
	 * @param password
	 */
	public SimpleClient(String username, String password)
	{
		this(username, password, Global.HOST);
	}
	
	public SimpleClient(String username, String password, String host)
	{
		this._gson = new Gson();
		this._locator = new Locator(username, host);
		this._client = new JsonClient(username, password, host);
	}
	
	public String getAuthToken()
	{
		return _authToken;
	}
	
	public void setAuthToken(String authToken)
	{
		_authToken = authToken;
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("sessionid", _authToken);
		cookie.setDomain("gpodder.net");
		cookieStore.addCookie(cookie);
		this._client.setCookieStore(cookieStore);
	}
	
	public boolean authenticate(String username, String password) throws ClientProtocolException, IOException, InvalidParameterException
	{
		HttpClient tempClient = new HttpClient();
		
		if(username != null && password!= null)
			tempClient.getCredentialsProvider().setCredentials(new AuthScope("gpodder.net", 443), new UsernamePasswordCredentials(username, password));
		else {
			throw new InvalidParameterException("Username and Password are required");
		}
		
		tempClient.POST(_locator.loginUri(), null);
		
		for(Cookie c : tempClient.getCookieStore().getCookies())
		{
			if(c.getName().equals("sessionid"));
				_authToken = c.getValue().toString();
		}
		
		return _authToken != null;
	}
	public List<String> getSubscriptions(String deviceId) throws ClientProtocolException, IOException
	{
		String uri = _locator.subscriptionsUri(deviceId);
		Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
		return _gson.fromJson(_client.GET(uri), collectionType);
	}
	
	public boolean putSubscriptions(String deviceId, List<String> urls) throws ClientProtocolException, IOException
	{
		String uri = _locator.subscriptionsUri(deviceId);
		String response = _client.PUT(uri, new StringEntity(_gson.toJson(urls)));
		return (response == "");
	}
	
	public List<Podcast> getSuggestions(int count) throws ClientProtocolException, IOException
	{
		String uri = _locator.suggestionsUri(count);
		Type collectionType = new TypeToken<ArrayList<Podcast>>(){}.getType();
		return _gson.fromJson(_client.GET(uri), collectionType);
	}
}