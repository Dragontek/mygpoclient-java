package com.dragontek.mygpoclient.Api;

import java.util.List;

/**
 *	Container for subscription changes
 *
 * @author jmondragon
 *
 */
public class SubscriptionChanges {
	
	/** A list of URLs that have been added */
	public List<String> add;
	/** A list of URLs that have been removed */
	public List<String> remove;
	/** A timestamp value for use in future requests */
	public long since;
	
	public SubscriptionChanges(List<String> add, List<String> remove, long since)
	{
		this.add = add;
		this.remove = remove;
		this.since = since;
	}
}