package com.dragontek.mygpoclient.feeds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.extras.GoogleReaderAPI;
import com.dragontek.mygpoclient.feeds.IFeed.IEpisode;

public class FeedServiceClientTest extends TestCase {
	FeedServiceClient feed_client;
	GoogleReaderAPI greader_client;
	String[] feed_urls = new String[] { "http://leo.am/podcasts/floss", "http://www.lightspeedmagazine.com/itunes-rss/", "http://revision3.com/trs/feed/MP4-hd30" };

	public FeedServiceClientTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		feed_client = new FeedServiceClient();
		greader_client = new GoogleReaderAPI();
	}
	
	@Test
	public void testFeedServiceClient() throws Exception {
		FeedServiceResponse response = feed_client.parseFeeds(feed_urls, 0L, true);
		assertEquals(feed_urls.length, response.size());
		testFeed(new ArrayList<IFeed>( response ));
	}
	
	@Test
	public void testGoogleReaderAPI() throws Exception {
		List<IFeed> response = greader_client.parseFeeds(feed_urls);
		assertEquals(feed_urls.length, response.size());
		testFeed(response);
	}
	
	private void testFeed(List<IFeed> feeds)
	{
		assertNotNull(feeds); // new ArrayList() is never null!
		
		for(IFeed f : feeds)
		{
			assertNotNull(f.getUrl());
			assertNotNull(f.getTitle());
			assertNotNull(f.getDescription());
			assertNotNull(f.getLink());
			assertNotNull(f.getEpisodes());
			
			for(IEpisode e : f.getEpisodes())
			{
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
