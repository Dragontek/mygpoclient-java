package com.dragontek.mygpoclient.api;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;

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
	public String type;
	/** The number of podcasts this device is subscribed to */
	public int subscriptions;

	public static String[] VALID_TYPES = new String[] { "desktop", "laptop", "mobile", "server", "other" };

	public PodcastDevice(String deviceId, String caption, String type, int subscriptions)
	{
		if(!Arrays.asList(VALID_TYPES).contains(type))
			throw new IllegalArgumentException(String.format("Invalid device type '%1' (see VALID_TYPES)", type));
			
		this.deviceId = deviceId;
		this.caption = caption;
		this.type = type;
		this.subscriptions = subscriptions;
	}
	
	@Override
	public String toString() {
		return String.format("%1(%2, %3, %4, %5)", this.getClass().getSimpleName(), this.deviceId, this.caption, this.type, this.subscriptions);
	}
	
	public PodcastDevice fromDictionary(Dictionary<String, String> m)
	{
		return new PodcastDevice(m.get("id"), m.get("caption"), m.get("type"), Integer.parseInt( m.get("subscriptions") ));
	}
}