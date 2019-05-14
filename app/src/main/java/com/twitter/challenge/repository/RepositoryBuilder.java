package com.twitter.challenge.repository;

public class RepositoryBuilder {
	private static TwitterRepository twitterRepository;

	public static TwitterRepository getTwitterRepository(){
		if(twitterRepository == null){
			twitterRepository = new TwitterRepository ();
		}
		return twitterRepository;
	}
}
