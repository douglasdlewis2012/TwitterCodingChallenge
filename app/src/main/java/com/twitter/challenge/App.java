package com.twitter.challenge;

import android.app.Application;

import com.twitter.challenge.api.TwitterAPI;
import com.twitter.challenge.tools.ConstantsKt;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class App extends Application {

	private static TwitterAPI mTwitterAPI;
	private static Retrofit retrofit;

	public static TwitterAPI getTwitterApi () {
		return mTwitterAPI;
	}

	private static TwitterAPI setupTwitterApi () {
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder ().addInterceptor (new TwitterInterceptor ());

		HttpLoggingInterceptor logging = new HttpLoggingInterceptor ();
		logging.setLevel (HttpLoggingInterceptor.Level.HEADERS);
		logging.setLevel (HttpLoggingInterceptor.Level.BODY);
		clientBuilder.addInterceptor (logging);

		retrofit = new Retrofit.Builder ().baseUrl (ConstantsKt.TWITTER_API)
				.addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
				.addConverterFactory (GsonConverterFactory.create ())
				.client (clientBuilder.build ())
				.build ();

		return retrofit.create (TwitterAPI.class);
	}

	private static TwitterAPI buildTwitterApi () {
		if (mTwitterAPI == null) {
			mTwitterAPI = setupTwitterApi ();
		}
		return mTwitterAPI;
	}

	@Override
	public void onCreate () {
		super.onCreate ();
		buildTimber ();
		buildTwitterApi ();
	}

	private void buildTimber () {
		if (BuildConfig.DEBUG) {
			Timber.plant (new Timber.DebugTree ());
		} else {
			// Don't build in release state
		}
	}

	private static class TwitterInterceptor implements Interceptor {

		@Override
		public Response intercept (Chain chain) throws IOException {
			final Request initialRequest = chain.request ();

			Request.Builder modifiedRequest = initialRequest.newBuilder ();
			modifiedRequest.addHeader ("Content-Type", "application/json");

			final Request finalRequest = modifiedRequest.build ();
			Response response = chain.proceed (finalRequest);

			return response;
		}
	}
}
