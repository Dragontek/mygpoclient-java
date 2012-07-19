package com.dragontek.mygpoclient.feeds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.dragontek.mygpoclient.json.JsonClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FeedServiceClient extends JsonClient {

	private static String BASE_URL="mygpo-feedservice.appspot.com";
	private String base_url;
	
	public FeedServiceClient()
	{
		this(BASE_URL);
	}

	public FeedServiceClient(String base_url){
		this(base_url, null, null);
	}

	public FeedServiceClient(String base_url, String username, String password)
	{ 
		super(username, password, base_url);
		this.base_url = base_url;
	}
	
	@Override
	public HttpRequest prepareRequest(String method, String uri, HttpEntity data) throws UnsupportedEncodingException {
		HttpRequest request = super.prepareRequest(method, uri, data);
		//request.addHeader("If-Modified-Since", "");
		//request.addHeader("Accept", "application/json");
		request.addHeader("Accept-Encoding", "gzip");
		return request;
	}
	
	
	public FeedServiceResponse parseFeeds(String[] feed_urls) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, 0L, false, true, false, 0, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, last_modified, false, true, false, 0, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified, boolean strip_html) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, last_modified, strip_html, true, false, 0, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified, boolean strip_html, boolean use_cache) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, last_modified, strip_html, use_cache, false, 0, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified, boolean strip_html, boolean use_cache, boolean inline_logo) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, last_modified, strip_html, use_cache, inline_logo, 0, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified, boolean strip_html, boolean use_cache, boolean inline_logo, int scale_logo) throws ClientProtocolException, IOException
	{
		return parseFeeds(feed_urls, last_modified, strip_html, use_cache, inline_logo, scale_logo, null);
	}
	public FeedServiceResponse parseFeeds(String[] feed_urls, long last_modified, boolean strip_html, boolean use_cache, boolean inline_logo, int scale_logo, String logo_format) throws ClientProtocolException, IOException
	{
		List<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("strip_html", strip_html ? "1" : "0" ));
		args.add(new BasicNameValuePair("use_cache", use_cache ? "1" : "0" ));
		args.add(new BasicNameValuePair("inline_logo", inline_logo ? "1" : "0" ));
		args.add(new BasicNameValuePair("scale_logo", Integer.toString(scale_logo)));
		args.add(new BasicNameValuePair("logo_format", logo_format));
		for(String feed_url : feed_urls)
		{
			args.add(new BasicNameValuePair("url", feed_url));
		}
		
		UrlEncodedFormEntity post_data = new UrlEncodedFormEntity(args, "UTF-8");
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<Feed>>(){}.getType();
		List<Feed> response = gson.fromJson(this.POST(this.base_url + "/parse" , post_data), collectionType);
		
		return new FeedServiceResponse(response, 0L, feed_urls);
	}
	
}
