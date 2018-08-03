package com.shoppinglist.rdproject.sunsetandsunrise;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static SunServiceAPI sunServiceAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

         retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sunrise-sunset.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sunServiceAPI = retrofit.create(SunServiceAPI.class);
    }

    public static SunServiceAPI getApi() {
        return sunServiceAPI;
    }
}
