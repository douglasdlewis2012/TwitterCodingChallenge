package com.twitter.challenge.events;

import com.twitter.challenge.model.TwitterWeatherModel;

import java.util.List;

public class WeatherForecastEvent {
	List<TwitterWeatherModel> weatherResponse;

	public WeatherForecastEvent (List<TwitterWeatherModel> weatherResponse) {
		this.weatherResponse = weatherResponse;
	}

	public List<TwitterWeatherModel> getWeatherResponse () {
		return weatherResponse;
	}

	public void setWeatherResponse (List<TwitterWeatherModel> weatherResponse) {
		this.weatherResponse = weatherResponse;
	}
}
