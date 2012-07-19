package com.dragontek.mygpoclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.feeds.FeedServiceClient;
import com.dragontek.mygpoclient.feeds.FeedServiceResponse;

import junit.framework.TestCase;

public class FeedServiceClientTest extends TestCase {
	FeedServiceClient client;
	public FeedServiceClientTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		client = new FeedServiceClient();
		
	}
	
	@Test
	public void testGetFeeds() throws Exception {
		String[] feeds = new String[] { "http://leo.am/podcasts/floss", "http://revision3.com/trs/feed/MP4-hd30" };
		FeedServiceResponse response = client.parseFeeds(feeds, 0L, true);
		assertNotNull(response); // new ArrayList() is never null!
		assertEquals(feeds.length, response.size());
	}
	
	
}
