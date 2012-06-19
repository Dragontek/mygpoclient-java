package com.dragontek.mygpoclient.simple;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dragontek.mygpoclient.Global;
import com.dragontek.mygpoclient.Locator;
import com.dragontek.mygpoclient.json.JsonClient;

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
	public Locator locator;
	public JsonClient client;
	
	public static String FORMAT = "FORMAT";
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
	}
	
	public String getAuthToken()
	{
		return "AuthTokenHere!";
	}
	public List<String> getSubscriptions(String deviceId) throws ClientProtocolException, IOException
	{
		List<String> list = new ArrayList<String>();
		String uri = locator.subscriptionsUri(deviceId);
		try {
			JSONArray array = new JSONArray(client.GET(uri));
			for(int i=0; i < array.length(); i++)
			{
				list.add(array.optString(i));
			}
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
		return list;
	}
	
	public boolean putSubscriptions(String deviceId, List<String> urls) throws ClientProtocolException, IOException
	{
		String uri = locator.subscriptionsUri(deviceId);
		JSONArray array = new JSONArray(urls);
		StringEntity data = null;
		try {
			data = new StringEntity(array.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = client.PUT(uri, data);
		System.out.println(String.format("RESPONSE:\r\n%s", response));
		return (response == "");
	}
	
	public List<Podcast> getSuggestions(int count) throws ClientProtocolException, IOException
	{
		List<Podcast> list = new ArrayList<Podcast>();

		String uri = locator.suggestionsUri(count);
		try {
			JSONArray array = new JSONArray(client.GET(uri));
			for(int i=0; i < array.length(); i++)
			{
				JSONObject json = array.optJSONObject(i);
				list.add( new Podcast( json ) );
			}
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
		return list;
	}
}