package com.twitter.challenge.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.twitter.challenge.R;
import com.twitter.challenge.model.TwitterWeatherModel;
import com.twitter.challenge.databinding.ActivityMainBinding;
import com.twitter.challenge.events.WeatherForecastEvent;
import com.twitter.challenge.tools.Helper;
import com.twitter.challenge.viewmodel.WeatherViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity implements BaseUI {

	private WeatherViewModel mViewModel = null;
	private ActivityMainBinding binding = null;

	public static void launchAsNewTask (Context context) {
		Intent intent = new Intent (context, MainActivity.class);
		intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity (intent);
	}

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setupViewModel ();
		setupUI ();
	}

	@Override
	protected void onStart () {
		super.onStart ();
		EventBus.getDefault ().register (this);

	}

	@Override
	protected void onStop () {
		super.onStop ();
		EventBus.getDefault ().unregister (this);
	}

	@Override
	public void setupUI () {
		binding = DataBindingUtil.setContentView (this, R.layout.activity_main);
		binding.setLifecycleOwner (this);
		binding.setWeather (mViewModel.getCurrentWeather ());
		setTitle (R.string.dashboard);

		if (mViewModel.getCurrentWeather () != null)
			setWeatherUI ();
	}

	@Override
	public void setupViewModel () {

		mViewModel = ViewModelProviders.of (this).get (WeatherViewModel.class);
		if (mViewModel.getCurrentWeather () == null) {
			downloadCurrentWeather ();
		}
	}

	private void downloadCurrentWeather () {
		mViewModel.downloadWeatherFromApi ().observe (this, new Observer<TwitterWeatherModel> () {
			@Override
			public void onChanged (@Nullable TwitterWeatherModel o) {
				mViewModel.setCurrentWeather (o);
				setWeatherUI ();

			}
		});
	}

	private void setWeatherUI () {
		binding.setWeather (mViewModel.getCurrentWeather ());
		binding.temperature.setText (Helper.Companion.formatTemperature (mViewModel.getCurrentWeather ().getWeather ().getTemp ().toString ()));
		setupCloudIfNecessary ();
	}

	private void setupCloudIfNecessary () {
		int cloudiness = mViewModel.getCurrentWeather ().getClouds ().getCloudiness ();
		if (cloudiness > 50) {
			setupCloudinessIcon (binding.weatherIcon);
		}
	}

	private void setupCloudinessIcon (ImageView image) {
		Picasso.get ().load (R.drawable.cloudcardicon).into (image);
	}

	private void downloadFiveDayForecast () {
		mViewModel.downloadFiveDayForecast ();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent (WeatherForecastEvent event) {
		mViewModel.setWeekOfResponses (event.getWeatherResponse ());
		binding.standardDeviation.setText (Helper.Companion.standardDeviation (mViewModel.getWeekOfWeather ()));
	}

	public void setupStandardDev (View v) {
		downloadFiveDayForecast ();
	}

}
