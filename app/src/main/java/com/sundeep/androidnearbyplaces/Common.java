package com.sundeep.androidnearbyplaces;

import com.sundeep.androidnearbyplaces.Models.MyPlaces;
import com.sundeep.androidnearbyplaces.Models.Results;
import com.sundeep.androidnearbyplaces.Remote.GoogleAPIServices;
import com.sundeep.androidnearbyplaces.Remote.RetrofitClient;
import com.sundeep.androidnearbyplaces.Remote.RetrofitScalarsClient;

public class Common {

    public static Results currentResult;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static GoogleAPIServices getGoogleAPIService(){

        return RetrofitClient.getClient(GOOGLE_API_URL).create(GoogleAPIServices.class);
    }

    public static GoogleAPIServices getGoogleAPIServiceScalars(){

        return RetrofitScalarsClient.getScalarClient(GOOGLE_API_URL).create(GoogleAPIServices.class);
    }
}
