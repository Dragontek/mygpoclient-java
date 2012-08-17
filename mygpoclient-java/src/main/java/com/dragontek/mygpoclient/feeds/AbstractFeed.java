package com.dragontek.mygpoclient.feeds;

public abstract class AbstractFeed implements IFeed {
	private String title;
	private String link;
	private String description;
	private String logoUrl;
	private String[] urls;
	private String logo;
	private IEpisode[] episodes;
	
	public String getTitle(){
		return title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public IEpisode[] getEpisodes() {
		return episodes;
	}

	public void setEpisodes(IEpisode[] episodes) {
		this.episodes = episodes;
	}
	
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public abstract class AbstractEpisode implements IEpisode {
		private String guid;
		private String title;
		private String description;
		private String link;
		private Long pubDate;
		private String author;
		private Long length;
		public String language;
		private IEnclosure[] enclosures;
		
		public abstract class AbstractEnclosure implements IEnclosure {
			private String url;
			private String mimetype;
			private Long length;
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getMimetype() {
				return mimetype;
			}
			public void setMimetype(String mimetype) {
				this.mimetype = mimetype;
			}
			public Long getLength() {
				return length;
			}
			public void setLength(Long length) {
				this.length = length;
			}
		}

		public String getGuid() {
			return guid;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public IEnclosure[] getFiles() {
			return enclosures;
		}

		public void setFiles(IEnclosure[] enclosures) {
			this.enclosures = enclosures;
		}

		public IEnclosure[] getEnclosures() {
			return enclosures;
		}

		public void setEnclosures(IEnclosure[] enclosures) {
			this.enclosures = enclosures;
		}

		public Long getPubDate() {
			return pubDate;
		}

		public void setPubDate(Long pubDate) {
			this.pubDate = pubDate;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Long getLength() {
			return length;
		}

		public void setLength(Long length) {
			this.length = length;
		}
	}

}
