package com.twitter.challenge.api;

import com.twitter.challenge.model.TwitterWeatherModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TwitterAPI {

	@GET("/current.json")
	Call<TwitterWeatherModel> getCurrentWeather ();

	@GET("/future_{day}.json")
	Observable<TwitterWeatherModel> getNthWeatherDay (@Path("day") int day);

}
