package com.sundeep.androidnearbyplaces.Remote;

import com.sundeep.androidnearbyplaces.Models.MyPlaces;
import com.sundeep.androidnearbyplaces.Models.PlaceDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GoogleAPIServices {

    @GET
    Call<MyPlaces> getNearByPlaces(@Url String Url);


    @GET
    Call<PlaceDetail> getDetailPlace(@Url String Url);

    @GET("maps/api/directions/json")
    Call<String> getDirections(@Query("origin") String origin, @Query("destination")String destination);
}
