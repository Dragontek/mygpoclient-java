package com.dragontek.mygpoclient.feeds;

import java.util.Dictionary;
import java.util.List;

public class Feed {
	public String title;
	public String link;
	public String description;
	public String author;
	public String language;
	public String[] urls;
	public String new_location;
	public String logo;
	public String logo_data;
	public String[] content_types;
	public String hub;
	public Dictionary<String, String> errors;
	public Dictionary<String, String> warnings;
	public long http_last_modified;
	public String http_etag;
	public Episode[] episodes;
	
	public class Episode {
		public String guid;
		public String title;
		public String short_title;
		public String number;
		public String description;
		public String link;
		public long released;
		public String author;
		public long duration;
		public String language;
		public Enclosure[] files;
		
		public class Enclosure {
			public String url;
			public String mimetype;
			public long filesize;
		}
	}
}
