package com.dragontek.mygpoclient.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import com.dragontek.mygpoclient.simple.SimpleClient;

public class MygPodderClientTest extends TestCase {
	private MygPodderClient client;

	private String _username;
	private String _password;
	private String _deviceId;
	private String _authToken;
	
	public MygPodderClientTest(String name) throws FileNotFoundException, IOException {
		super(name);
		
		// NOTE: You should create a client.properties file with these values
		// in the root (next to pom.xml)
		Properties properties = new java.util.Properties();
		properties.load(new FileInputStream("client.properties"));
		_username = properties.getProperty("username");
		_password = properties.getProperty("password");
		_deviceId = properties.getProperty("deviceId");
		client = new MygPodderClient(_username, _password);
		
	}

	public void testLogin() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		assertTrue(client.authenticate(_username, _password));
		_authToken = client.getAuthToken();
		assertNotNull(_authToken);
	}
	
	public void testGetAuthToken() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		if(client.authenticate(_username, _password))
		{
			String token = client.getAuthToken();
			System.out.println(token);
			assertNotNull(token);
		}
	}
	
	public void testSetAuthToken() throws Exception
	{
		String token = null;
		if(client.authenticate(_username, _password))
		{
			token = client.getAuthToken();
			System.out.println(String.format("TOKEN: %s", token));
			assertNotNull(token);
		}		
		MygPodderClient tempClient = new MygPodderClient(_username);
		tempClient.setAuthToken(token);
		tempClient.getSubscriptions(_deviceId);
		
	}
	
	public void testGetDeviceSync() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		DeviceSync syncstatus = client.getDeviceSync();
		assertNotNull(syncstatus);
		for(String notsynced : syncstatus.notsynced)
		{
			System.out.println(notsynced);
		}
		for(String[] synced : syncstatus.synced)
		{
			System.out.println(synced.toString());
		}
	}

}
