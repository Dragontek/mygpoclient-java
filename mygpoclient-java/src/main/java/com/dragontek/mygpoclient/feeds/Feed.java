package com.dragontek.mygpoclient.feeds;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class Feed implements IFeed {
	private String title;
	private String link;
	private String description;
	private String author;
	private String language;
	private String[] urls;
	private String new_location;
	private String logo;
	private String logo_data;
	private String[] content_types;
	private String hub;
	/* These are disabled because the new feed service doesn't support them the same
	 * way that the old service did.  Errors and warnings aren't coming back as dictionary
	 * and http_last_modified is a date string rather than UNIX timestamp
	 */
	//private Dictionary<String, String> errors;
	//private Dictionary<String, String> warnings;
	//private long http_last_modified;
	private String http_etag;
	private Episode[] episodes;
	public String getTitle()
	{
		return this.title;
	}
	public String getDescription()
	{
		return this.description;
	}
	public String getLink()
	{
		return this.link;
	}
	public String getUrl()
	{
		return this.urls[0];
	}
	public String getLogoUrl()
	{
		return this.logo;
	}
	public IEpisode[] getEpisodes()
	{
		return this.episodes;
	}
	public class Episode implements IEpisode {
		private String guid;
		private String title;
		private String short_title;
		private String number;
		private String description;
		private String link;
		private long released;
		private String author;
		private long duration;
		private String language;
		private Enclosure[] files;

		public IEnclosure getEnclosure(){
			return files[0];
		}
		
		public class Enclosure implements IEnclosure {
			private String url;
			private String[] urls;
			private String mimetype;
			private long filesize;
			
			@Override
			public String getUrl() {
				if(url != null)
					return url;
				else
					return urls[0];
			}
			@Override
			public String getMimetype() {
				return mimetype;
			}
			@Override
			public long getFilesize() {
				return filesize;
			}
		}


		@Override
		public String getGuid() {
			return guid;
		}
		@Override
		public String getTitle() {
			return title;
		}
		@Override
		public String getDescription() {
			return description;
		}
		@Override
		public String getLink() {
			return link;
		}
		@Override
		public long getReleased() {
			return released;
		}

		public String getNumber() {
			return number;
		}
		public String getShort_title() {
			return short_title;
		}
		public String getAuthor() {
			return author;
		}
		public long getDuration() {
			return duration;
		}
		public String getLanguage() {
			return language;
		}
	}
}
