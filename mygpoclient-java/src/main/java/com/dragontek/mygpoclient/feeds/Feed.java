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
	public Long http_last_modified;
	public String http_etag;
	public List<Episode> episodes;
	
	public class Episode {
		public String guid;
		public String title;
		public String short_title;
		public String number;
		public String description;
		public String link;
		public Long released;
		public String author;
		public Long duration;
		public String language;
		public List<Enclosure> files;
		
		public class Enclosure {
			public String url;
			public String mimetype;
			public Long length;
		}
	}
}
