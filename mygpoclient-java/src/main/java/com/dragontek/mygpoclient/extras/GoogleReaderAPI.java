package com.dragontek.mygpoclient.extras;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.dragontek.mygpoclient.feeds.IFeed;
import com.dragontek.mygpoclient.http.HttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Implementation of some of the basic Google Reader API
 * methods.
 *  
 * @author joshua.mondragon
 *
 */
public class GoogleReaderAPI extends HttpClient {

	private String mActionToken = null;
	private String BASE_API_URI = "http://www.google.com/reader/api/0/";
	
	public final static String CURRENT_USER = "user/-/";
	public final static String STATE_READ = "state/com.google/read";
	public final static String STATE_STARRED = "state/com.google/starred";
	public final static String STATE_KEPT_UNREAD = "state/com.google/kept-unread";
	public final static String STATE_FRESH = "state/com.google/fresh";
	
	public void requestActionToken() throws AuthenticationException, IOException
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("client", "Podder"));
		mActionToken =  GET(BASE_API_URI + "token"); 
	}
	public void mark(List<String> ids, String state) throws AuthenticationException, IOException
	{
		mark(ids, state, null);
	}	

	public void mark(List<String> ids, String state, String feedUrl) throws AuthenticationException, IOException
	{
        if(mActionToken == null)
        	requestActionToken();
        if(ids.isEmpty())
        	return;
        
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for(String id : ids)
        {
    		nameValuePairs.add(new BasicNameValuePair("i", id));
        }
        if(feedUrl != null) // Feed Url is not required, but makes the call more efficient
        	nameValuePairs.add(new BasicNameValuePair("s", "feed/" + feedUrl));
        nameValuePairs.add(new BasicNameValuePair("T", mActionToken));
        nameValuePairs.add(new BasicNameValuePair("a", CURRENT_USER + state));
        
        POST(BASE_API_URI + "edit-tag", new UrlEncodedFormEntity(nameValuePairs));
	}
	public void unMark(List<String> ids, String state) throws AuthenticationException, IOException
	{
		unMark(ids, state, null);
	}
	public void unMark(List<String> ids, String state, String feedUrl) throws AuthenticationException, IOException
	{
        if(mActionToken == null)
        	requestActionToken();

        if(ids.isEmpty())
        	return;
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for(String id : ids)
        {
        	nameValuePairs.add(new BasicNameValuePair("i", id));
        }
        if(feedUrl != null) // Feed Url is not required, but makes the call more efficient
        	nameValuePairs.add(new BasicNameValuePair("s", "feed/" + feedUrl));
        
        nameValuePairs.add(new BasicNameValuePair("T", mActionToken));
        nameValuePairs.add(new BasicNameValuePair("r", CURRENT_USER + state));
        
        POST(BASE_API_URI + "edit-tag", new UrlEncodedFormEntity(nameValuePairs));
	}
	
	public List<IFeed> parseFeeds(String[] feed_urls) throws IOException
	{

		ArrayList<IFeed> feeds = new ArrayList<IFeed>();
		for(String feed : feed_urls)
		{
			feeds.add(parse(feed));
		}
		return feeds;
	}
	public GoogleFeed parse(String url) throws IOException
	{
		
		Gson gson = new Gson();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
       	// TODO: Optimize with since timestamp and no comments 
		//nameValuePairs.add(new BasicNameValuePair("ot", timestamp));
		nameValuePairs.add(new BasicNameValuePair("comments", "false"));
		nameValuePairs.add(new BasicNameValuePair("likes", "false"));
		String response = GET(BASE_API_URI + "stream/contents/feed/" +  URLEncoder.encode(url, "UTF-8"));
		return gson.fromJson(response, GoogleFeed.class);
	}
	public void putSubscription(String feedUrl, String label) throws AuthenticationException, UnsupportedEncodingException, IOException
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
       	nameValuePairs.add(new BasicNameValuePair("s", "feed/" + feedUrl));
        nameValuePairs.add(new BasicNameValuePair("ac", "subscribe"));
        nameValuePairs.add(new BasicNameValuePair("a", "user/-/label/" + label));
        //nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("T", mActionToken));
        
        POST(BASE_API_URI + "subscription/edit", new UrlEncodedFormEntity(nameValuePairs));
		
	}
	public void setLabel(String feedUrl, String label) throws AuthenticationException, UnsupportedEncodingException, IOException
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
       	nameValuePairs.add(new BasicNameValuePair("s", "feed/" + feedUrl));
        nameValuePairs.add(new BasicNameValuePair("ac", "edit"));
        nameValuePairs.add(new BasicNameValuePair("a", "user/-/label/" + label));
        //nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("T", mActionToken));
        
        POST(BASE_API_URI + "subscription/edit", new UrlEncodedFormEntity(nameValuePairs));
		
	}
	public List<String> getSubscriptions(String label) throws AuthenticationException, IOException
	{
		String response = GET(BASE_API_URI + "subscription/list?output=json");
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
		return gson.fromJson(response, collectionType);
	}
}
