package com.dragontek.mygpoclient.pub;

import java.util.List;

import org.junit.Test;

import com.dragontek.mygpoclient.pub.ClientConfig;
import com.dragontek.mygpoclient.pub.PublicClient;
import com.dragontek.mygpoclient.pub.ToplistPodcast;
import com.dragontek.mygpoclient.simple.Podcast;

import junit.framework.TestCase;

public class PublicClientTest extends TestCase {

	PublicClient client;
	public PublicClientTest(String name)
	{
		super(name);
		client = new PublicClient();
		
	}
	
	@Test
	public void testGetClientConfig() throws Exception {
		ClientConfig config = client.getConfiguration();
		assertNotNull(config);
		assertNotNull(config.mygpo);
		assertNotNull(config.mygpo_feedservice);
		
		System.out.println( "mygpo: " + config.mygpo.get("baseurl") );
		System.out.println( "mygpo_feedservice: " +config.mygpo_feedservice.get("baseurl"));
		System.out.println( "update_timeout: " +config.update_timeout );
		
		
	}
	
	@Test
	public void testGetTopList() throws Exception {
		
		List<ToplistPodcast> podcasts = client.getToplist();
		assertNotNull(podcasts); // new ArrayList() is never null!
		assertEquals(25, podcasts.size());
		
		for(ToplistPodcast podcast : podcasts)
		{
			System.out.println(podcast.url);
		}
		
	}
	
	@Test
	public void testSearchPodcast() throws Exception {
		
		List<Podcast> podcasts = client.searchPodcast("Linux");
		assertNotNull(podcasts);
		for(Podcast podcast : podcasts)
		{
			System.out.println(podcast.url);
		}
		
	}
}
