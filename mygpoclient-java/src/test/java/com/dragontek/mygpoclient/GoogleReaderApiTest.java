package com.dragontek.mygpoclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.feeds.FeedServiceClient;
import com.dragontek.mygpoclient.feeds.FeedServiceResponse;
import com.dragontek.mygpoclient.feeds.GoogleReaderAPI;
import com.dragontek.mygpoclient.pub.iTunesSearchApi;
import com.dragontek.mygpoclient.simple.Podcast;

import junit.framework.TestCase;

public class GoogleReaderApiTest extends TestCase {
	GoogleReaderAPI client;
	public GoogleReaderApiTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		client = new GoogleReaderAPI();
		
	}
	
	@Test
	public void testSearch() throws Exception {
		
		List<String> podcasts = client.getSubscriptions("Podder");
		
		for(String p : podcasts)
		{
			System.out.println(p);
			
		}
	}
	
	
}
