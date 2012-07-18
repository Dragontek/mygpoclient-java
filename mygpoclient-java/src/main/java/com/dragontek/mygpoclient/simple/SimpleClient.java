package com.dragontek.mygpoclient.simple;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;

import com.dragontek.mygpoclient.Global;
import com.dragontek.mygpoclient.Locator;
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
	protected Locator locator;
	protected JsonClient client;
	protected Gson _gson;
	public static String FORMAT = "json";
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
		this.locator = new Locator(username, host);
		this.client = new JsonClient(username, password, host);
		this._gson = new Gson();
	}
	
	public String getAuthToken()
	{
		return "AuthTokenHere!";
	}
	public List<String> getSubscriptions(String deviceId) throws ClientProtocolException, IOException
	{
		String uri = locator.subscriptionsUri(deviceId);
		Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
		return _gson.fromJson(client.GET(uri), collectionType);
	}
	
	public boolean putSubscriptions(String deviceId, List<String> urls) throws ClientProtocolException, IOException
	{
		String uri = locator.subscriptionsUri(deviceId);
		String response = client.PUT(uri, new StringEntity(_gson.toJson(urls)));
		return (response == "");
	}
	
	public List<Podcast> getSuggestions(int count) throws ClientProtocolException, IOException
	{
		String uri = locator.suggestionsUri(count);
		Type collectionType = new TypeToken<ArrayList<Podcast>>(){}.getType();
		return _gson.fromJson(client.GET(uri), collectionType);
	}
}