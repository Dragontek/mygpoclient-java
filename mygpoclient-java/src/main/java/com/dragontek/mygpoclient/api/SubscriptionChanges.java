package com.dragontek.mygpoclient.api;

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
	public long timestamp;
	
	public SubscriptionChanges(List<String> add, List<String> remove)
	{
		this(add, remove, 0L); //or should timestamp be now?
	}
	public SubscriptionChanges(List<String> add, List<String> remove, long timestamp)
	{
		this.add = add;
		this.remove = remove;
		this.timestamp = timestamp;
	}
}