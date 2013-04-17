package com.dragontek.mygpoclient.feeds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.feeds.Feed.Episode;

public class FeedServiceClientTest extends TestCase {
	FeedServiceClient feed_client;
	String[] feed_urls = new String[] { "http://leo.am/podcasts/floss", "http://www.lightspeedmagazine.com/itunes-rss/", "http://revision3.com/trs/feed/MP4-hd30" };
	
	public FeedServiceClientTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		feed_client = new FeedServiceClient();
	}
	
	@Test
	public void testFeedServiceClient() throws Exception {
		FeedServiceResponse response = feed_client.parseFeeds(feed_urls, 1366057557396L, true);
		assertEquals(feed_urls.length, response.size());
		testFeed(new ArrayList<Feed>( response ));
	}
	
	private void testFeed(List<Feed> feeds)
	{
		assertNotNull(feeds); // new ArrayList() is never null!
		
		for(Feed f : feeds)
		{
			assertNotNull(f.getUrl());
			assertNotNull(f.getTitle());
			assertNotNull(f.getDescription());
			assertNotNull(f.getLink());
			assertNotNull(f.getEpisodes());
			
			for(Episode e : f.getEpisodes())
			{
				//System.out.println(new Date(e.getReleased() * 1000).toGMTString() + " -- " + e.getTitle());
				// TODO: Assert feed_update_timestamp > e.getReleased
				assertNotNull(e.getGuid());
				assertNotNull(e.getTitle());
				assertNotNull(e.getDescription());
				// Not all feeds include a link;
				//assertNotNull(e.getLink());
				assertNotNull(e.getEnclosure());
				assertNotNull(e.getEnclosure().getUrl());
				assertNotNull(e.getEnclosure().getMimetype());
				assertNotNull(e.getEnclosure().getFilesize());
			}
		}
		
	}
}
