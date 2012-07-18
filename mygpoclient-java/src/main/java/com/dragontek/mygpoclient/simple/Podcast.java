package com.dragontek.mygpoclient.simple;

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
	public int subscribers_last_week;
	/** Link to the website for the podcast */
	public String website;
	/** URL for the logo image of the podcast */
	public String logo_url;
	
	public Podcast(String url, String title, String description)
	{
		this.url = url;
		this.title = title;
		this.description = description;
	}
	
}