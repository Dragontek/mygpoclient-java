package com.dragontek.mygpoclient.pub;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.dragontek.mygpoclient.json.JsonClient;
import com.dragontek.mygpoclient.simple.Podcast;
import com.google.gson.Gson;

public class iTunesSearchApi {

	private String BASE_URL = "http://itunes.apple.com/search?";
	private String FIND_URL = BASE_URL + "media=podcast&entity=podcast&term=";
	private JsonClient client;
	public iTunesSearchApi()
	{
		client = new JsonClient("itunes.apple.com");
	}
	public ArrayList<Podcast> search(String query) throws IOException
	{
		// TODO: I'm sure there's a better way to do this with Gson, but as a quick fix, I'm manually
		// creating a Podcast arraylist from entity's
		String response = client.GET(FIND_URL + URLEncoder.encode(query, "UTF-8"));
		
		ArrayList<Podcast> podcasts = new ArrayList<Podcast>();
		Gson gson = new Gson();
		
		Entity entity = gson.fromJson(response, Entity.class);
		for(Result r : entity.results)
		{
			Podcast p = new Podcast(r.feedUrl, r.collectionName, r.artistName);
			if(r.artworkUrl100 != null)
				p.logo_url = r.artworkUrl100;
			
			
			podcasts.add(p);
		}
		return podcasts;
	}

	public class Entity
	{
		int resultCount;
		ArrayList<Result> results;
	}
	public class Result
	{
		public String kind;
		public String artistName;
		public String feedUrl;
		public String collectionName;
		public String trackName;
		public String artworkUrl30;
		public String artworkUrl60;
		public String artworkUrl100;
	}
	
}
