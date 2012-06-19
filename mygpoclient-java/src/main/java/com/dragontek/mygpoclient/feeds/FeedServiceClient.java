package com.dragontek.mygpoclient.feeds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import com.dragontek.mygpoclient.Util;
import com.dragontek.mygpoclient.json.JsonClient;

public class FeedServiceClient extends JsonClient {

	public static String BASE_URL="mygpo-feedservice.appspot.com";
	
	private String base_url;
	
	public FeedServiceClient()
	{
		this(null, null, BASE_URL);
	}
	// TODO: The rest of the overloaded constructors?
	public FeedServiceClient(String username, String password, String base_url)
	{ 
		super(username, password, base_url);
		this.base_url = base_url;
	}

	@Override
	public HttpRequest prepareRequest(String method, String uri, HttpEntity data) {
		
		HttpRequest request = super.prepareRequest(method, uri, data);
		request.addHeader("Accept", "application/json");
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
		args.add(new BasicNameValuePair("strip_html", Boolean.toString(strip_html)));
		args.add(new BasicNameValuePair("use_cache", Boolean.toString(use_cache)));
		args.add(new BasicNameValuePair("inline_logo", Boolean.toString(inline_logo)));
		args.add(new BasicNameValuePair("scale_logo", Integer.toString(scale_logo)));
		args.add(new BasicNameValuePair("logo_format", logo_format));
		String url = buildUrl(args);
		
		List<NameValuePair> urls = new ArrayList<NameValuePair>();
		for(String feed_url : feed_urls)
		{
			urls.add(new BasicNameValuePair("url", feed_url));
		}
		
		UrlEncodedFormEntity post_data = null;
		try {
			post_data = new UrlEncodedFormEntity(urls);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONArray response = new JSONArray();
		try {
			response = new JSONArray(this.POST(url, post_data));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RESPONSE: " + response.toString());
		
		return new FeedServiceResponse(response, 0L, feed_urls);
	}
	public String buildUrl(List<NameValuePair> kwargs)
	{
		String args = URLEncodedUtils.format(kwargs, "UTF8");
		String query_url = Util.join(new String[]{this.base_url, "parse"});
		return String.format("%s?%s", query_url, args);
				
	}
	
}
