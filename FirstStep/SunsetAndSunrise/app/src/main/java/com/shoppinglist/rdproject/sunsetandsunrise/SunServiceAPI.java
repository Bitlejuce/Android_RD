package com.shoppinglist.rdproject.sunsetandsunrise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SunServiceAPI {

    @GET("json")
    Call<TimeInfo> getTimeInfo(@Query("lat") String latitude, @Query("lng") String longitude);

//    @GET("json?lat=36.7201600&lng=-4.4203400")
//    Call<TimeInfo> getTimeInfo();
}
