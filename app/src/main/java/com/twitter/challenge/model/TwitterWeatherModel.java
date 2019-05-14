package com.twitter.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TwitterWeatherModel {

	@SerializedName("coord")
	@Expose
	private Coord coord;
	@SerializedName("weather")
	@Expose
	private Weather weather;
	@SerializedName("wind")
	@Expose
	private Wind wind;
	@SerializedName("rain")
	@Expose
	private Rain rain;
	@SerializedName("clouds")
	@Expose
	private Clouds clouds;
	@SerializedName("name")
	@Expose
	private String name;

	public Coord getCoord () {
		return coord;
	}

	public void setCoord (Coord coord) {
		this.coord = coord;
	}

	public Weather getWeather () {
		return weather;
	}

	public void setWeather (Weather weather) {
		this.weather = weather;
	}

	public Wind getWind () {
		return wind;
	}

	public void setWind (Wind wind) {
		this.wind = wind;
	}

	public Rain getRain () {
		return rain;
	}

	public void setRain (Rain rain) {
		this.rain = rain;
	}

	public Clouds getClouds () {
		return clouds;
	}

	public void setClouds (Clouds clouds) {
		this.clouds = clouds;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

}
