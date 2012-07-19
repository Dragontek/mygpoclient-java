package com.dragontek.mygpoclient.feeds;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class FeedServiceResponse extends ArrayList<Feed> {

	/*
	 *     def __init__(self, feeds, last_modified, feed_urls):
        super(FeedServiceResponse, self).__init__(feeds)
        self.last_modified = last_modified
        self.feed_urls = feed_urls
        self.indexed_feeds = {}
        for feed in feeds:
            for url in feed['urls']:
                self.indexed_feeds[url] = feed


    def get_feeds(self):
        """
        Returns the parsed feeds in order of the initial request
        """
        return (self.get_feed(url) for url in self.feed_urls)


    def get_feed(self, url):
        """
        Returns the parsed feed for the given URL
        """
        return self.indexed_feeds.get(url, None)
	 */
	
	/**
	 * 
	 */
	public long last_modfied;
	public String[] feed_urls;
	public String[] indexed_feeds = {};
	
	public FeedServiceResponse(List<Feed> feeds, long last_modified, String[] feed_urls)
	{
		super.addAll(feeds);
	}
	
	public void getFeeds()
	{
		
	}
	
	public void getFeed()
	{
		
	}
}
