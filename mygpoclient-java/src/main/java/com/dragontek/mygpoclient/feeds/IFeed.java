package com.dragontek.mygpoclient.feeds;


// TODO: Determine if we need setters since these feeds
//		will be populated via Gson
public interface IFeed {

	public String getTitle();
	public String[] getUrls();
	public String getDescription();
	public String getLink();
	public String getLogoUrl();
	public IEpisode[] getEpisodes();
	
	public interface IEpisode
	{
		public String getTitle();
		public String getDescription();
		public IEnclosure[] getEnclosures();
		
		public interface IEnclosure
		{
			public String getURL();
			public String getMimeType();
			public Long getLength();
		}
	}
}
