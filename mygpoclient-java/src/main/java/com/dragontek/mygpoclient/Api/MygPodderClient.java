package com.dragontek.mygpoclient.Api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dragontek.mygpoclient.Simple.SimpleClient;

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

	public List<String> getSubscriptions(PodcastDevice device) throws ClientProtocolException, IOException {
		return super.getSubscriptions(device.deviceId);
	}
	
	public boolean putSubscriptions(PodcastDevice device, List<String> urls) throws ClientProtocolException, IOException {
		return super.putSubscriptions(device.deviceId, urls);
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
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public UpdateResult updateSubscriptions(String deviceId, List<String> addUrls, List<String> removeUrls) throws ClientProtocolException, IOException
	{
		String uri = locator.addRemoveSubscriptionsUri(deviceId);
		JSONObject json = new JSONObject();
		try {
			if(addUrls != null)
				json.putOpt("add", new JSONArray(addUrls));
			if(removeUrls != null)
				json.putOpt("remove", new JSONArray(removeUrls));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringEntity data = null;
		try {
			data = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = client.POST(uri, data);

		Map<String, String> updateUrls = new HashMap<String, String>();
		long timestamp = 0;
		
		System.out.println(String.format("RESPONSE:\r\n%s", response));
		try {
			JSONObject updates = new JSONObject(response);
			JSONArray urls = updates.getJSONArray("update_urls");
			for(int i=0; i < urls.length(); i++)
			{
				JSONArray url = urls.getJSONArray(i);
				updateUrls.put(url.getString(0), url.getString(1));
			}
			timestamp = updates.getLong("timestamp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new UpdateResult(updateUrls, timestamp);
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
	public SubscriptionChanges pullSubscriptions(String deviceId, long since) throws ClientProtocolException, JSONException, IOException
	{
		String uri = locator.subscriptionUpdatesUri(deviceId, since);
		List<String> addList = new ArrayList<String>();
		List<String> removeList = new ArrayList<String>();
		JSONObject json = new JSONObject(client.GET(uri));
		JSONArray addUrls = json.optJSONArray("add");
		for(int i=0; i < addUrls.length(); i++)
		{
			addList.add(addUrls.optString(i));
		}
		JSONArray removeUrls = json.optJSONArray("remove");
		for(int i=0; i < removeUrls.length(); i++)
		{
			removeList.add(removeUrls.optString(i));
		}
		since = json.optLong("timestamp");
		return new SubscriptionChanges(addList, removeList, since);
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
		String uri = locator.uploadEpisodeActionsUri();
		// TODO: Change to List<NameValuePair> and UrlEncoded
		//System.out.println( new JSONObject(actions) );
		JSONArray array = new JSONArray();
		for(EpisodeAction action : actions)
		{
			JSONObject json = new JSONObject();
			try {
				json.put("podcast", action.podcast);
				json.put("episode", action.episode);
				json.put("action", action.action);
				json.putOpt("device", action.deviceId);
				json.putOpt("position", action.position);
				json.putOpt("started", action.started);
				json.putOpt("total", action.total);
				json.putOpt("timestamp", action.timestamp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			array.put(json);
		}
		StringEntity data = null;
		try {
			data = new StringEntity(array.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = client.POST(uri, data);

		System.out.println(String.format("RESPONSE:\r\n%s", response));
		try {
			JSONObject jsonResponse = new JSONObject(response);
			return jsonResponse.getLong("timestamp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0L;
		}
		
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
		List<EpisodeAction> list = new ArrayList<EpisodeAction>();
		String uri = locator.downloadEpisodeActionsUri(since, podcast, deviceId);
			
		try {
			JSONObject json = new JSONObject(client.GET(uri));
			JSONArray actions = json.optJSONArray("actions");
			for(int i=0; i < actions.length(); i++)
			{
				JSONObject action = actions.optJSONObject(i);
				list.add(
						new EpisodeAction(
								action.optString("podcast"), 
								action.optString("episode"), 
								action.optString("action"), 
								action.optString("device"), 
								action.optString("timestamp"), 
								action.optInt("started"), 
								action.optInt("position"), 
								action.optInt("total"))
						);
			}
			since = json.optLong("timestamp");
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
		return new EpisodeActionChanges(list, since);
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
		String uri = locator.deviceSettingsUri(deviceId);
		JSONObject json = new JSONObject();
		try {
			json.putOpt("caption", caption);
			json.putOpt("type", type);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringEntity data = null;
		try {
			data = new StringEntity(json.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = client.POST(uri, data);
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
		String uri = locator.deviceListUri();
		List<PodcastDevice> list = new ArrayList<PodcastDevice>();
		try {
			JSONArray devices = new JSONArray(client.GET(uri));
			for(int i=0; i < devices.length(); i++)
			{
				JSONObject device = devices.optJSONObject(i);
				list.add(new PodcastDevice(device.optString("id"), device.optString("caption"), device.optString("type"), device.optInt("subscriptions")));
			}
		} catch (JSONException jex) {
			jex.printStackTrace();
		}
		return list;
	}
}