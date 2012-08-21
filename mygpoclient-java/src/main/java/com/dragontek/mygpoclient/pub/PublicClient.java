package com.dragontek.mygpoclient.pub;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.dragontek.mygpoclient.Global;
import com.dragontek.mygpoclient.Locator;
import com.dragontek.mygpoclient.json.JsonClient;
import com.dragontek.mygpoclient.simple.IPodcast;
import com.dragontek.mygpoclient.simple.Podcast;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
	private Gson _gson;
	
	/**
	 * Creates a new Public API client
	 */
	public PublicClient()
	{
		this(Global.HOST);
	}

	/**
	 * Creates a new Public API client
	 * 
	 * @param host hostname of the webservice (gpodder.net)
	 */
	public PublicClient(String host)
	{
		this._locator = new Locator(host);
		this._client = new JsonClient();
		this._gson = new Gson();
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
	public List<IPodcast> getToplist(int count) throws JsonSyntaxException, ClientProtocolException, IOException
	{
		String uri = _locator.toplistUri(count);
		Type collectionType = new TypeToken<ArrayList<Podcast>>(){}.getType();
		return _gson.fromJson(_client.GET(uri), collectionType);
	}
	
	/**
	 * Get a list of most-subscribed podcasts
	 * @return Returns a list of {@link ToplistPodcast} objects.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<IPodcast> getToplist() throws JsonSyntaxException, ClientProtocolException, IOException
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
	public List<IPodcast> searchPodcast(String query) throws JsonSyntaxException, ClientProtocolException, IOException
	{
		String uri = _locator.searchUri(URLEncoder.encode(query, "UTF-8")); 
		Type collectionType = new TypeToken<ArrayList<Podcast>>(){}.getType();
		return _gson.fromJson(_client.GET(uri), collectionType);
	}
	
	public Podcast getPodcastData(String url) throws ClientProtocolException, IOException
	{
		String uri = _locator.getPodcastDataUri(url);
		return _gson.fromJson(_client.GET(uri), Podcast.class);
	}
	
	public ClientConfig getConfiguration() throws JsonSyntaxException, ClientProtocolException, IOException
	{
		String uri = _locator.clientConfigUri();
		return _gson.fromJson(_client.GET(uri), ClientConfig.class);
	}
}
