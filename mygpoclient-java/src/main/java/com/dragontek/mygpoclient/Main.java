package com.dragontek.mygpoclient;

import com.dragontek.mygpoclient.pub.PublicClient;
import com.dragontek.mygpoclient.pub.ToplistPodcast;
import com.dragontek.mygpoclient.simple.Podcast;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("mygpoclient");
		PublicClient client = new PublicClient();
		try {
			List<Podcast> podcasts = client.searchPodcast("linux");

			for(Podcast podcast : podcasts)
			{
				System.out.println(podcast.title);
			}
		} catch(Exception e) {
			System.out.println("ERROR");
		}
	}

}
