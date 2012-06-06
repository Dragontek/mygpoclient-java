package com.dragontek.mygpoclient.Api;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

/**
 * This class encapsulates an episode action
 * 
 * @author jmondragon
 *
 */
public class EpisodeAction {
	/** The feed URL of the podcast */
	public String podcast;
	/** The enclosure URL or GUID of the episode */
	public String episode;
	/** One of 'download', 'play', 'delete' or 'new' */
	public String action;
	/** The device_id on which the action has taken place */
	public String deviceId;
	/** When the action took place (in XML time format) */
	public String timestamp;
	/** The start time of a play event in seconds */
	public Integer started = null;
	/** The current position of a play event in seconds */
	public Integer position = null;
	/** The total time of the episode (for play events) */
	public Integer total = null;

	public static List<String> VALID_ACTIONS =  Arrays.asList("download", "play", "delete", "new");

	public EpisodeAction(String podcast, String episode, String action, String deviceId, String timestamp, Integer started, Integer position, Integer total)
	{
        // Check if the action is valid
		if(!VALID_ACTIONS.contains(action))
		{
			throw new InvalidParameterException(String.format("Invalid action type '%s' (see VALID_TYPES)", action));
		}

        // Disallow play-only attributes for non-play actions
		if(action != "play")
		{
			/*
			if(started != null)
				System.out.println("started:" + started);
			if(position != null)
				System.out.println("position:" + position);
			if(total != null)
				System.out.println("total:" + total);
			*/
		}

        // Check the format of the timestamp value
        if(timestamp != null)
        {
            //    if util.iso8601_to_datetime(timestamp) is None:
            //        raise ValueError('Timestamp has to be in ISO 8601 format but was %s' % timestamp)
        }

        // Check if we have a "position" value if we have started or total
        if(position != null && (started != null | total != null))
        {
              //  raise ValueError('Started or total set, but no position given')
        }

        this.podcast = podcast;
        this.episode = episode;
        this.action = action;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.started = started;
        this.position = position;
        this.total = total;

	}
}