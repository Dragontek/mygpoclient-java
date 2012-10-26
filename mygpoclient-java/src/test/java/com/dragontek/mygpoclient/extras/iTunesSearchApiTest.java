package com.dragontek.mygpoclient.extras;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.dragontek.mygpoclient.simple.IPodcast;

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
