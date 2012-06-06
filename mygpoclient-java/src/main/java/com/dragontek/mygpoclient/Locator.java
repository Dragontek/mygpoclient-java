package com.dragontek.mygpoclient;

public class Locator {
	String _username;
	String _simpleBase;
	String _base;
	

	public Locator(String username)
	{
		this(username, Global.HOST);
	}
	public Locator(String username, String host)
	{
		this(username, host, Global.VERSION);
	}
	
	public Locator(String username, String host, int version)
	{
		this._username = username;
		this._simpleBase = String.format("http://%s", host);
		this._base = String.format("http://%s/api/%s", host, version);
	}
	
	public String subscriptionsUri(String deviceId)
	{
		return subscriptionsUri(deviceId, "json");
	}
	public String subscriptionsUri(String deviceId, String format)
	{
		//TODO: Assert format is valid or throw error.
		String filename = String.format("%s.%s", deviceId, format);
		return Util.join(new String[] { this._simpleBase, "subscriptions", this._username, filename } );
	}
	
	
	public String toplistUri()
	{
		return toplistUri(25);
	}
	public String toplistUri(int count)
	{
		return toplistUri(count, "json");
	}
	public String toplistUri(int count, String format)
	{
		//TODO: Assert count and format are valid or throw error.
		String filename = String.format("toplist/%s.%s", count, format);
		return Util.join(new String[] { this._simpleBase, filename } );
	}
	
	
	public String suggestionsUri()
	{
		return suggestionsUri(10);
	}
	public String suggestionsUri(int count)
	{
		return toplistUri(count, "json");
	}
	public String suggestionsUri(int count, String format)
	{
		//TODO: Assert count and format are valid or throw error.
		String filename = String.format("suggestions/%s.%s", count, format);
		return Util.join(new String[] { this._simpleBase, filename } );
	}
	
	public String searchUri(String query)
	{
		return searchUri(query, "json");
	}
	public String searchUri(String query, String format)
	{
		//TODO: Assert format is valid or throw error.
		String filename = String.format("search.%s?q=%s", format, query);
		return Util.join(new String[] { this._simpleBase, filename } );
	}
	
	public String addRemoveSubscriptionsUri(String deviceId)
	{
		String filename = String.format("%s.json", deviceId);
		return Util.join(new String[] { this._base, "subscriptions", this._username, filename} );
	}
	
	public String subscriptionUpdatesUri(String deviceId)
	{
		return subscriptionUpdatesUri(deviceId, 0);
	}
	public String subscriptionUpdatesUri(String deviceId, long since)
	{
		String filename = String.format("%s.json?since=%s", deviceId, since);
		
		return Util.join(new String[] { this._base, "subscriptions", this._username, filename });
	}
	
	public String uploadEpisodeActionsUri()
	{
		String filename = String.format("%s.json", this._username);
		return Util.join(new String[] { this._base, "episodes", filename } );
	}
	
	public String downloadEpisodeActionsUri(long since, String podcast, String deviceId)
	{
		String filename = String.format("%s.json?device=%s&since=%s&aggregated=true", this._username, deviceId, since);
		if(podcast != null)
			filename = String.format("%s.json?podcast=%s&device=%s&since=%s&aggregated=true", this._username, podcast, deviceId, since);
		
		return Util.join(new String[] { this._base, "episodes", filename });
	}
	
	public String downloadEpisodeActionsUri(long since, String deviceId)
	{
		return downloadEpisodeActionsUri(since, null, deviceId);
	}
	
	public String deviceSettingsUri(String deviceId)
	{
		String filename = String.format("%s.json", deviceId);
		return Util.join(new String[] { this._base, "devices", this._username, filename });
	}
	
	public String deviceListUri()
	{
		String filename = String.format("%s.json", this._username);
		return Util.join(new String[] { this._base, "devices", filename });
	}
	
	public String getPodcastDataUri(String podcastUrl)
	{
		String filename = String.format("podcast.json?url=%s", podcastUrl);
		return Util.join(new String[] { this._base, "data", filename });
	}
	public String getEpisodeDataUri(String podcastUrl, String episodeUrl)
	{
		String filename = String.format("episode.json?podcast=%s&url=%s", podcastUrl, episodeUrl);
		return Util.join(new String[] { this._base, "data", filename });
	}
}
