package com.dragontek.mygpoclient.Api;

import java.util.Map;

/**
 * Container for subscription update results
 *
 * @author jmondragon
 * 
 */
public class UpdateResult {
	
	/** A list of (old_url, new_url) tuples */
	public Map<String, String> updateUrls;
	/** A timestamp value for use in future requests */
	public long since;
	
	public UpdateResult(Map<String, String> updateUrls, long since)
	{
		this.updateUrls = updateUrls;
		this.since = since;
	}
}