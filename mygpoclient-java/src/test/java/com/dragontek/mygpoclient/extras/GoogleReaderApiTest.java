package com.dragontek.mygpoclient.extras;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

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
