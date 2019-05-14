package com.twitter.challenge.viewmodel;

import android.arch.lifecycle.LiveData;

import com.twitter.challenge.model.TwitterWeatherModel;
import com.twitter.challenge.repository.RepositoryBuilder;
import com.twitter.challenge.repository.TwitterRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class WeatherViewModel extends BaseViewModel {

	private TwitterWeatherModel currentWeather;
	private List<TwitterWeatherModel> weekOfResponses;
	private List<Double> weekOfWeather;
	private TwitterRepository repository = RepositoryBuilder.getTwitterRepository ();

	public WeatherViewModel () {

	}

	public List<Double> getWeekOfWeather () {
		return weekOfWeather;
	}

	private void setWeekOfWeatherTemperature () {
		List<Double> weather = new ArrayList<> ();
		for (TwitterWeatherModel twr : weekOfResponses) {
			weather.add (twr.getWeather ().getTemp ());
		}
		this.weekOfWeather = weather;
	}

	public List<TwitterWeatherModel> getWeekOfResponses () {
		return weekOfResponses;
	}

	public void setWeekOfResponses (List<TwitterWeatherModel> weekOfResponses) {
		this.weekOfResponses = weekOfResponses;
		setWeekOfWeatherTemperature ();
	}

	public TwitterWeatherModel getCurrentWeather () {
		return currentWeather;
	}

	public void setCurrentWeather (TwitterWeatherModel currentWeather) {
		this.currentWeather = currentWeather;
	}

	public LiveData downloadWeatherFromApi () {
		return repository.getWeatherFromTwitter ();
	}

	public Observable downloadFiveDayForecast () {
		return repository.getFiveDayWeatherFromTwitter ();
	}

}
