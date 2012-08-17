package com.dragontek.mygpoclient.simple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;

import com.dragontek.mygpoclient.api.MygPodderClient;
import com.dragontek.mygpoclient.simple.SimpleClient;

import junit.framework.TestCase;

public class SimpleClientTest extends TestCase {

	private SimpleClient client;

	private String _username;
	private String _password;
	private String _deviceId;
	private String _authToken;
	
	public SimpleClientTest(String name) throws FileNotFoundException, IOException {
		super(name);
		
		// NOTE: You should create a client.properties file with these values
		// in the root (next to pom.xml)
		Properties properties = new java.util.Properties();
		properties.load(new FileInputStream("client.properties"));
		_username = properties.getProperty("username");
		_password = properties.getProperty("password");
		_deviceId = properties.getProperty("deviceId");
		client = new SimpleClient(_username, _password);
		
	}

	public void testLogin() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		assertTrue(client.authenticate(_username, _password));
		_authToken = client.getAuthToken();
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
	
	public void testGetSubscriptions() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		List<String> subscriptions = client.getSubscriptions(_deviceId);
		assertNotNull(subscriptions);
		for(String subscription : subscriptions)
		{
			System.out.println(subscription);
		}
	}
}
