package com.sundeep.androidnearbyplaces.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitScalarsClient {

    private   static Retrofit instance = null;


    public static Retrofit getScalarClient(String baseUrl) {


        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return  instance;
    }
}
