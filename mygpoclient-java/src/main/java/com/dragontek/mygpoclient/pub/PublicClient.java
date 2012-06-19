package com.dragontek.mygpoclient.pub;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.dragontek.mygpoclient.Global;
import com.dragontek.mygpoclient.Locator;
import com.dragontek.mygpoclient.json.JsonClient;
import com.dragontek.mygpoclient.simple.Podcast;

/**
 * Client for the gpodder.net "anonymous" API
 * <p>
 * This is the API client implementation that provides a
 * Java interface to the parts of the gpodder.net
 * Simple API that don't need user authentication.
 * 
 * @author joshua.mondragon
 *
 */
public class PublicClient
{
	public Locator _locator;
	public JsonClient _client;
	public static String FORMAT = "json";
	
	/**
	 * Creates a new Public API client
	 * 
	 * @param host hostname of the webservice (gpodder.net)
	 */
	public PublicClient(String host)
	{
		this._locator = new Locator(host);
		this._client = new JsonClient(host);
	}
	
	/**
	 * Creates a new Public API client
	 */
	public PublicClient()
	{
		this(Global.HOST);
	}
	
	/**
	 * Get a list of most-subscribed podcasts
	 * 
	 * @param count the amount of podcasts that are returned. The minimum value is 1 and
        the maximum value is 100.
	 * @return Returns a list of {@link ToplistPodcast} objects.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<ToplistPodcast> getToplist(int count) throws ClientProtocolException, IOException
	{
		String uri = _locator.toplistUri(count);
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<ToplistPodcast>>(){}.getType();
		List<ToplistPodcast> toplist = gson.fromJson(_client.GET(uri), collectionType);
		return toplist;
	}
	
	/**
	 * Get a list of most-subscribed podcasts
	 * @return Returns a list of {@link ToplistPodcast} objects.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<ToplistPodcast> getToplist() throws ClientProtocolException, IOException
	{
		return getToplist(Global.TOPLIST_DEFAULT);
	}
	
	/**
	 * Search for podcasts on the webservice
	 * @param query  specifies the search query as a string
	 * @return Returns a list of Podcast objects.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<Podcast> searchPodcast(String query) throws ClientProtocolException, IOException
	{
		String uri = null;
		List<Podcast> list = new ArrayList<Podcast>();

		try {
			uri = _locator.searchUri(URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONArray array = new JSONArray(_client.GET(uri));
			for(int i=0; i < array.length(); i++)
			{
				JSONObject json = array.optJSONObject(i);
				list.add( new Podcast( json ) );
			}
		} catch (Exception jex) {
			jex.printStackTrace();
		}
		return list;
	}
	
	public Podcast getPodcastData(String url) throws ClientProtocolException, IOException
	{
		String uri = _locator.getPodcastDataUri(url);
		try {
			JSONObject json = new JSONObject(_client.GET(uri));
			return new Podcast(json);
		} catch (Exception jex) {
			jex.printStackTrace();
			return null;
		}
	}
}
