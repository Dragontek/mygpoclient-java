package com.dragontek.mygpoclient.extras;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.dragontek.mygpoclient.IPodcast;
import com.dragontek.mygpoclient.json.JsonClient;
import com.google.gson.Gson;

public class iTunesSearchApi {

	private String BASE_URL = "http://itunes.apple.com/search?";
	private String FIND_URL = BASE_URL + "media=podcast&entity=podcast&term=";
	private JsonClient client;
	public iTunesSearchApi()
	{
		client = new JsonClient();
	}
	
	public List<IPodcast> searchPodcast(String query) throws IOException
	{
		String response = client.GET(FIND_URL + URLEncoder.encode(query, "UTF-8"));
		
		Gson gson = new Gson();
		Entity entity = gson.fromJson(response, Entity.class);
		
		List<IPodcast> results = new ArrayList<IPodcast>(); // entity.results;
		results.addAll(entity.results);

		return results;
	}

	public class Entity
	{
		int resultCount;
		ArrayList<Result> results;
	}

	public class Result implements IPodcast
	{
		private String kind;
		private String artistName;
		private String feedUrl;
		private String collectionName;
		private String trackName;
		private String artworkUrl30;
		private String artworkUrl60;
		private String artworkUrl100;
		
		@Override
		public String getUrl() {
			return this.feedUrl;
		}
		
		@Override
		public String getTitle() {
			return this.trackName;
		}
		
		@Override
		public String getDescription() {
			return this.artistName;
		}
		
		@Override
		public String getLogoUrl() {
			if(this.artworkUrl100 != null)
				return this.artworkUrl100;
			else if(this.artworkUrl60 != null)
				return this.artworkUrl60;
			else
				return this.artworkUrl30;
		}

		@Override
		public void setTitle(String title) {
			this.trackName = title;
		}

		@Override
		public void setDescription(String description) {
			this.artistName = description;
		}

		@Override
		public void setLogoUrl(String logourl) {
			this.artworkUrl100 = logourl;
		}
	}
	
}
