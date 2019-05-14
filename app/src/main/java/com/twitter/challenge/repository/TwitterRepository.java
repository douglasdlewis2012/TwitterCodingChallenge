package com.twitter.challenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.twitter.challenge.App;
import com.twitter.challenge.model.TwitterWeatherModel;
import com.twitter.challenge.events.WeatherForecastEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TwitterRepository {

	public LiveData<TwitterWeatherModel> getWeatherFromTwitter () {
		final MutableLiveData<TwitterWeatherModel> liveData = new MutableLiveData<> ();
		App.getTwitterApi ().getCurrentWeather ().enqueue (new Callback<TwitterWeatherModel> () {
			@Override
			public void onResponse (Call<TwitterWeatherModel> call, Response<TwitterWeatherModel> response) {
				if (response.isSuccessful () && response.code () == 200) {
					TwitterWeatherModel twr = response.body ();
					liveData.setValue (twr);
				}
			}

			@Override
			public void onFailure (Call<TwitterWeatherModel> call, Throwable t) {
				Timber.e ("Failed request");
			}
		});

		return liveData;
	}

	public Observable<List<TwitterWeatherModel>> getFiveDayWeatherFromTwitter () {
		Observable<TwitterWeatherModel> apiCall1 = App.getTwitterApi ().getNthWeatherDay (1).subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ());
		Observable<TwitterWeatherModel> apiCall2 = App.getTwitterApi ().getNthWeatherDay (2).subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ());
		Observable<TwitterWeatherModel> apiCall3 = App.getTwitterApi ().getNthWeatherDay (3).subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ());
		Observable<TwitterWeatherModel> apiCall4 = App.getTwitterApi ().getNthWeatherDay (4).subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ());
		Observable<TwitterWeatherModel> apiCall5 = App.getTwitterApi ().getNthWeatherDay (5).subscribeOn (Schedulers.newThread ()).observeOn (AndroidSchedulers.mainThread ());

		Observable<List<TwitterWeatherModel>> combined = Observable.zip (apiCall1, apiCall2, apiCall3, apiCall4, apiCall5, new Function5<TwitterWeatherModel, TwitterWeatherModel, TwitterWeatherModel, TwitterWeatherModel, TwitterWeatherModel, List<TwitterWeatherModel>> () {
			@Override
			public List<TwitterWeatherModel> apply (TwitterWeatherModel twitterWeatherResponse, TwitterWeatherModel twitterWeatherResponse2, TwitterWeatherModel twitterWeatherResponse3, TwitterWeatherModel twitterWeatherResponse4, TwitterWeatherModel twitterWeatherResponse5) throws Exception {
				List<TwitterWeatherModel> combine = new ArrayList<> ();

				combine.add (twitterWeatherResponse);
				combine.add (twitterWeatherResponse2);
				combine.add (twitterWeatherResponse3);
				combine.add (twitterWeatherResponse4);
				combine.add (twitterWeatherResponse5);

				return combine;
			}

		});

		combined.subscribe (new Observer<List<TwitterWeatherModel>> () {
			@Override
			public void onSubscribe (Disposable d) {
				Timber.d ("onSubscribe");

			}

			@Override
			public void onNext (List<TwitterWeatherModel> twitterWeatherResponses) {
				Timber.d ("onNext");
				EventBus.getDefault ().post (new WeatherForecastEvent (twitterWeatherResponses));
			}

			@Override
			public void onError (Throwable e) {
				Timber.d ("OnError");

			}

			@Override
			public void onComplete () {
				Timber.d ("onComplete");

			}
		});
		return combined;

	}
}
