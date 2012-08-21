package com.dragontek.mygpoclient.extras;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.extras.iTunesSearchApi;
import com.dragontek.mygpoclient.simple.IPodcast;

import junit.framework.TestCase;

public class iTunesSearchApiTest extends TestCase {
	iTunesSearchApi client;
	public iTunesSearchApiTest(String name) throws ClientProtocolException, IOException
	{
		super(name);
		client = new iTunesSearchApi();
		
	}
	
	@Test
	public void testSearch() throws Exception {
		List<IPodcast> podcasts = client.searchPodcast("Crime Central");
		for(IPodcast p : podcasts)
		{
			System.out.println(p.getUrl());
		}
	}
	
	
}
