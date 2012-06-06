package com.dragontek.mygpoclient.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for added episode actions
 * 
 * @author jmondragon
 * @see EpisodeAction
 */
public class EpisodeActionChanges {
	
	/** A list of EpisodeAction objects */
	public List<EpisodeAction> _actions = new ArrayList<EpisodeAction>();
	/** A timestamp value for use in future requests */
	public long _since = 0;
	
	public EpisodeActionChanges(List<EpisodeAction> actions, long since)
	{
		this._actions = actions;
		this._since = since;
	}
}