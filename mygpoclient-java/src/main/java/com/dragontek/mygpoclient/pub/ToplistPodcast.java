package com.dragontek.mygpoclient.pub;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Container class for a toplist entry
 * 
 * This class encapsulates the metadata for a podcast
 * in the podcast toplist.
 * 
 * @author joshua.mondragon
 *
 */
public class ToplistPodcast
{
	/** The feed URL of the podcast */
	public String url;
	/** The title of the podcast */
	public String title;
	/** The description of the podcast */
	public String description;
	/** The current subscriber count */
	public int subscribers;
	/** Last week's subscriber count */
	public int subscribersLastWeek;
	
	// TODO: Shouldn't this just extend Podcast?
	
	public ToplistPodcast(String url, String title, String description, int subscribers, int subscribersLastWeek)
	{
		this.url = url;
		this.title = title;
		this.description = description;
		this.subscribers = subscribers;
		this.subscribersLastWeek = subscribersLastWeek;
	}
	
	public ToplistPodcast(JSONObject json)
	{
		try {
			this.url = json.getString("url");
			this.title = json.getString("title");
			this.description = json.getString("description");
			this.subscribers = json.getInt("subscribers");
			this.subscribersLastWeek = json.getInt("subscribersLastWeek");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}
}
