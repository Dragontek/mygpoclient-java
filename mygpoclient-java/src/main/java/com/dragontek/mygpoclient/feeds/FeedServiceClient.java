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
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.dragontek.mygpoclient.json.JsonClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FeedServiceClient extends JsonClient {

	private static String HOST="http://feeds.gpodder.net";

	public FeedServiceClient()
	{
		this(HOST);
	}

	public FeedServiceClient(String host){
		this(host, null, null);
	}

	public FeedServiceClient(String host, String username, String password)
	{ 
		super(username, password);
		HOST = host;
	}
	
	@Override
	public HttpUriRequest prepareRequest(String method, String uri, HttpEntity data) throws UnsupportedEncodingException {
		HttpUriRequest request = super.prepareRequest(method, uri, data);
		// TODO: Implement this if-modified-since
		//request.addHeader("If-Modified-Since", "");
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
		if(strip_html)
			args.add(new BasicNameValuePair("process_text", "strip_html"));
		if(use_cache)
			args.add(new BasicNameValuePair("use_cache", "1"));
		if(inline_logo)
		{
			args.add(new BasicNameValuePair("inline_logo", "1"));
			args.add(new BasicNameValuePair("scale_logo", Integer.toString(scale_logo)));
		}
		if(logo_format != null && !logo_format.isEmpty())
			args.add(new BasicNameValuePair("logo_format", logo_format));

		for(String feed_url : feed_urls)
		{
			args.add(new BasicNameValuePair("url", feed_url));
		}
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<Feed>>(){}.getType();

		// POST
		List<Feed> response = gson.fromJson(this.POST(HOST + "/parse", new UrlEncodedFormEntity(args, "UTF-8")), collectionType);
		// GET
		//List<Feed> response = gson.fromJson(this.GET(HOST + "/parse?" + URLEncodedUtils.format(args, "UTF-8")), collectionType);	

		return new FeedServiceResponse(response, 0L, feed_urls);
	}
	
}
