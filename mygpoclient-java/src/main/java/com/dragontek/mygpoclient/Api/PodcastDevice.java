package com.dragontek.mygpoclient.Api;

/**
 * This class encapsulates a podcast device
 *
 * @author jmondragon
 *
 */

public class PodcastDevice {
	/** The ID used to refer to this device */
	public String deviceId;
	/** A user-defined "name" for this device */
	public String caption;
	/** A valid type of podcast device (see VALID_TYPES) */
	public String _type;
	/** The number of podcasts this device is subscribed to */
	public int _subscriptions;

	private static String[] VALID_TYPES = new String[] { "desktop", "laptop", "mobile", "server", "other" };

	public PodcastDevice(String deviceId, String caption, String type, int subscriptions)
	{
		if(!Arrays.asList(VALID_TYPES).contains(type))
		{
			// TODO: Throw error	
		}
			
		this.deviceId = deviceId;
		this.caption = caption;
		this._type = type;
		this._subscriptions = subscriptions;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}