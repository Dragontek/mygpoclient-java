package com.dragontek.mygpoclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.dragontek.mygpoclient.simple.SimpleClient;

import junit.framework.TestCase;

public class SimpleClientTest extends TestCase {

	private SimpleClient client;

	private String _username;
	private String _password;
	private String _deviceId;
	
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

	public void testGetAuthToken() throws Exception
	{
		System.out.println(String.format("** %s **", this.getName()) );
		String token = client.getAuthToken();
		assertNotNull(token);
		System.out.println(token);
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
