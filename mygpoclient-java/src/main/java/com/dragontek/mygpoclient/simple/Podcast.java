package com.dragontek.mygpoclient.simple;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Container class for a podcast
 * Encapsulates the metadata for a podcast
 * 
 * @author joshua.mondragon
 *
 */
public class Podcast {
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
	/** Link to the website for the podcast */
	public String link;
	/** URL for the logo image of the podcast */
	
	public String imageUrl;
	
	public Podcast(JSONObject json)
	{
		try {
			this.url		= json.getString("url");
			this.title		= json.getString("title");
			this.description= json.getString("description");
			this.subscribers = json.getInt("subscribers");
			this.subscribersLastWeek = json.getInt("subscribers_last_week");
			this.link		= json.getString("website");
			this.imageUrl	= json.getString("logo_url");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Podcast(String url, String title, String description)
	{
		this.url = url;
		this.title = title;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}
}