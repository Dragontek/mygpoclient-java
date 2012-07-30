package com.dragontek.mygpoclient.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;

import com.dragontek.mygpoclient.simple.SimpleClient;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 *	This is the API client that implements both the Simple and
 *	Advanced API of gpodder.net. See the {@link SimpleClient} class
 *	for a smaller class that only implements the Simple API.
 * 
 * @see SimpleClient
 * @author jmondragon
 * @version 2.0
 */
public class MygPodderClient extends SimpleClient
{
	/**
	 * This is the API client that implements both the Simple and
	 * Advanced API of gpodder.net. See the {@link SimpleClient} class
	 * for a smaller class that only implements the Simple API.
	 * @param username
	 * @param password
	 */
	public MygPodderClient(String username, String password) {
		super(username, password);
	}
	public MygPodderClient(String username) {
		super(username);
	}
	
	public List<String> getSubscriptions(PodcastDevice device) throws ClientProtocolException, IOException {
		return super.getSubscriptions(device.id);
	}
	
	public boolean putSubscriptions(PodcastDevice device, List<String> urls) throws ClientProtocolException, IOException {
		return super.putSubscriptions(device.id, urls);
	}

	
	/**
	 * Update the subscription list for a given device.
	 * <p>
	 * Returns a {@link UpdateResult} object that contains a list of (sanitized)
     * URLs and a "since" value that can be used for future calls to.
	 * <p>
     * For every (old_url, new_url) tuple in the updated_urls list of
     * the resulting object, the client should rewrite the URL in its
     * subscription list so that new_url is used instead of old_url.
	 * 
	 * @param deviceId		the id of the device to be updated 
	 * @param addUrls		a list of urls to be added to the device
	 * @param removeUrls	a list of urls to be removed from the device
	 * @return a {@link UpdateResult} object that contains a list of (sanitized)
     * URLs and a "since" value that can be used for future calls to
     * {@link pullSubscriptions}.
	 * @throws JsonSyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public UpdateResult updateSubscriptions(String deviceId, List<String> add, List<String> remove) throws JsonSyntaxException, ClientProtocolException, IOException
	{
		String uri = _locator.addRemoveSubscriptionsUri(deviceId);
		SubscriptionChanges changes = new SubscriptionChanges(add, remove);
		StringEntity data = new StringEntity(_gson.toJson(changes));
		return _gson.fromJson(_client.POST(uri, data), UpdateResult.class);
	}
	
	/**
	 * Downloads subscriptions since the time of the last update
	 * 
	 * @param deviceId the id of the device to be updated
	 * @param since should be a timestamp that has been retrieved previously by a call to {@link #updateSubscriptions} or {@link #pullSubscriptions}.
	 * @return a {@link SubscriptionChanges} object with two lists (one for
        added and one for removed podcast URLs) and a "since" value
        that can be used for future calls to this method.
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws ClientProtocolException 
	 */
	public SubscriptionChanges pullSubscriptions(String deviceId, long since) throws ClientProtocolException, IOException
	{
		String uri = _locator.subscriptionUpdatesUri(deviceId, since);
		return _gson.fromJson(_client.GET(uri), SubscriptionChanges.class);
	}
	
    /**
     * Uploads a {@link List} of {@link EpisodeAction} objects to the server
     * @param actions a {@link List} of {@link EpisodeAction} objects
     * @return a timestamp that can be used for retrieving changes.
     * @throws IOException 
     * @throws ClientProtocolException 
     */
	public long uploadEpisodeActions(List<EpisodeAction> actions) throws ClientProtocolException, IOException
	{
		String uri = _locator.uploadEpisodeActionsUri();
		StringEntity data = new StringEntity(_gson.toJson(actions));
		String response = _client.POST(uri, data);
		// TODO: Parse timestamp from response
		return 0L;
	}
	
	/**
	 * Downloads a {@link List} of {@link EpisodeAction} objects from the server
	 * 
	 * @param since
	 * @param podcast
	 * @param deviceId
	 * @return a {@link EpisodeActionChanges} object with the list of
        new actions and a "since" timestamp that can be used for
        future calls to this method when retrieving episodes.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public EpisodeActionChanges downloadEpisodeActions(long since, String podcast, String deviceId) throws ClientProtocolException, IOException
	{
		String uri = _locator.downloadEpisodeActionsUri(since, podcast, deviceId);
		return _gson.fromJson(_client.GET(uri), EpisodeActionChanges.class);
	}
	public EpisodeActionChanges downloadEpisodeActions(long since, String deviceId) throws ClientProtocolException, IOException
	{
		return downloadEpisodeActions(since, null, deviceId);
	}
	
    /**
     * Update the description of a device on the server
     * <p>
     * This changes the caption and/or type of a given device
     * on the server. If the device does not exist, it is
     * created with the given settings.
     * The parameters caption and type are both optional and
     * when set to a value other than None will be used to
     * update the device settings.
     * 
     * @param deviceId
     * @param caption
     * @param type
     * @return true if the request succeeded, false otherwise.
     * @throws IOException 
     * @throws ClientProtocolException 
     */
	public boolean updateDeviceSettings(String deviceId, String caption, String type) throws ClientProtocolException, IOException
	{
		String uri = _locator.deviceSettingsUri(deviceId);
		PodcastDevice device = new PodcastDevice(deviceId, caption, type);
		StringEntity data = new StringEntity( _gson.toJson(device), "UTF-8");
		String response = _client.POST(uri, data);
		return response.equals("");
	}
	
	/**
	 * Returns a {@link List} of this user's {@link PodcastDevice} objects
	 * <p>
	 * The resulting list can be used to display a selection
	 * list to the user or to determine device IDs to pull
	 * the subscription list from.
	 * 
	 * @return a {@link List} of this user's {@link PodcastDevice} objects
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<PodcastDevice> getDevices() throws ClientProtocolException, IOException
	{
		String uri = _locator.deviceListUri();
		Type collectionType = new TypeToken<ArrayList<PodcastDevice>>(){}.getType();
		return _gson.fromJson(_client.GET(uri), collectionType);
	}
}