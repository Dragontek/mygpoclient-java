package com.dragontek.mygpoclient;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.feeds.FeedServiceClient;
import com.dragontek.mygpoclient.feeds.FeedServiceResponse;
import com.dragontek.mygpoclient.pub.iTunesSearchApi;
import com.dragontek.mygpoclient.simple.Podcast;

import junit.framework.TestCase;

public class ItunesSearchApiTest extends TestCase {
	iTunesSearchApi client;
	public ItunesSearchApiTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		client = new iTunesSearchApi();
		
	}
	
	@Test
	public void testSearch() throws Exception {
		ArrayList<Podcast> podcasts = client.search("Crime Central");
		for(Podcast p : podcasts)
		{
			System.out.println(p.url);
			
		}
	}
	
	
}
