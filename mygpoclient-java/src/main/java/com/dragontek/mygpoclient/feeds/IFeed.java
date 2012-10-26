package com.dragontek.mygpoclient.feeds;


/**
 * A standard interface that is used for all Feed classes.
 * Classes implementing these are used for updating podcast
 * feeds.
 * 
 * @author jmondragon
 *
 */
// TODO: Determine if we need setters since these feeds
//		will be populated via Gson for now we won't 
//		require them from the interface.
public interface IFeed {

	public String getTitle();
	public String getUrl();
	public String getDescription();
	public String getLink();
	// I would like to do this, but not all feeds include logo
	//public String getLogoUrl();

	public IEpisode[] getEpisodes();
	public interface IEpisode
	{
		public String getGuid();
		public String getTitle();
		public String getDescription();
		public long getReleased();
		public String getLink();
		public String getAuthor();
		
		public IEnclosure getEnclosure();
		public interface IEnclosure
		{
			public String getUrl();
			public String getMimetype();
			public long getFilesize();
		}
	}
}
