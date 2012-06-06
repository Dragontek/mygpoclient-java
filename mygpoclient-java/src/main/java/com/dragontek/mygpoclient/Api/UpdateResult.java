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
	protected Map<String, String> _updateUrls;
	/** A timestamp value for use in future requests */
	protected long _since;
	
	public UpdateResult(Map<String, String> updateUrls, long since)
	{
		this._updateUrls = updateUrls;
		this._since = since;
	}
}