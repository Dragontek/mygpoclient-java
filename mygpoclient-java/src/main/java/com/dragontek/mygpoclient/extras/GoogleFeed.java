package com.dragontek.mygpoclient.extras;

import com.dragontek.mygpoclient.feeds.IFeed;


public class GoogleFeed implements IFeed {

    private String id;
	private String title;
    private String description;
    private Alternate[] alternate;
    private Item[] items;
	
    @Override
	public String getTitle() {
		return this.title;
	}
	@Override
	public String getUrl() {
		
		return this.id.replaceFirst("feed/", "");
	}
	@Override
	public String getDescription() {
		return this.description;
	}
	@Override
	public String getLink() {
		if(this.alternate != null && this.alternate.length > 0)
			return this.alternate[0].href;
		else
			return null;
	}
	@Override
	public IEpisode[] getEpisodes() {
		return this.items;
	}

	public class Alternate
    {
    	public String href;
    	public String type;
    }
	
    public class Item implements IEpisode
    {
        private String id;
        private Enclosure[] enclosure;
        private Alternate[] alternate;
        private String author;
        private String title;
        private Content content;
        private Content summary;
        private long published;
        private long updated;
        private long crawlTimeMsec;
        private String[] categories;
		private Origin origin;

        public boolean isState(String state)
        {
        	for(String category : categories)
        	{
        		if(category.endsWith(state))
        			return true;
        	}
        	return false;
        }

		@Override
		public String getGuid() {
			return this.id;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public String getDescription() {
			if(this.content != null)
				return this.content.content;
			else
				return this.summary.content;
		}

		@Override
		public long getReleased() {
			return this.published;
		}
		public long getUpdated() {
			return this.updated;
		}
		
		public Origin getOrigin() {
			return this.origin;
		}
		
		public long getCrawlTimeMsec() {
			return this.crawlTimeMsec;
		}
		@Override
		public String getLink() {
			if(this.alternate != null && this.alternate.length > 0)
				return this.alternate[0].href;
			else
				return null;
		}

		@Override
		public IEnclosure getEnclosure() {
			return this.enclosure[0];
		}

		@Override
		public String getAuthor() {
			return this.author;
		}

	    public class Enclosure implements IEnclosure
	    {
	    	public String href;
	    	public String type;
	    	public long length;
			
	    	@Override
			public String getUrl() {
				return this.href;
			}
			@Override
			public String getMimetype() {
				return this.type;
			}
			@Override
			public long getFilesize() {
				return this.length;
			}
	    }
		
		public class Content
	    {
	    	public String direction;
	    	public String content;
	    }
	    public class Summary
	    {
	    	public String direction;
	    	public String content;
	    }
	    public class Alternate
	    {
	    	public String href;
	    	public String type;
	    }
	    public class Origin
	    {
	    	public String htmlUrl;
	    	public String streamId;
	    	public String title;
	    }
		
    }
	
}
