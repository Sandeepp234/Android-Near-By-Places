package com.sundeep.androidnearbyplaces;

import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sundeep.androidnearbyplaces.Models.Photos;
import com.sundeep.androidnearbyplaces.Models.PlaceDetail;
import com.sundeep.androidnearbyplaces.Remote.GoogleAPIServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlace extends AppCompatActivity {


    ImageView photo;
    RatingBar ratingBar;
    TextView opening_hour, place_address, place_name;
    Button btnViewOnMap, btnViewDirection;


    GoogleAPIServices mServices;
    PlaceDetail mPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        mServices = Common.getGoogleAPIService();

        photo = findViewById(R.id.photo);

        ratingBar = findViewById(R.id.rating_bar);

        place_address = findViewById(R.id.place_address);

        place_name = findViewById(R.id.place_name);

        opening_hour = findViewById(R.id.place_open_hour);

        btnViewOnMap = findViewById(R.id.btn_show_map);

        btnViewDirection = findViewById(R.id.btn_view_direction);

        place_name.setText("");
        place_address.setText("");
        opening_hour.setText("");

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });

        btnViewDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(ViewPlace.this, ViewDirection.class);
                startActivity(mapIntent);
            }
        });

///photo
        if (Common.currentResult.getPhotos() != null && Common.currentResult.getPhotos().length > 0) {
            Picasso.with(this)
                    .load(getPhotoOfPlace(Common.currentResult.getPhotos()[0].getPhoto_reference(), 1000))
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .into(photo);
        }
////rating
        if (Common.currentResult.getRating() != null && !TextUtils.isEmpty(Common.currentResult.getRating())) {
            ratingBar.setRating(Float.parseFloat(Common.currentResult.getRating()));
        } else {
            ratingBar.setVisibility(View.GONE);
        }

        //opening hour

        if (Common.currentResult.getOpening_hours() != null) {
            opening_hour.setText("Open now :" + Common.currentResult.getOpening_hours().getOpen_now());
        } else {
            opening_hour.setVisibility(View.GONE);
        }

        //User services to fetch address and name

        mServices.getDetailPlace(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {

                        mPlace = response.body();

                        place_address.setText(mPlace.getResult().getFormatted_address());
                        place_name.setText(mPlace.getResult().getName());

                    }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });


    }

    private String getPlaceDetailUrl(String place_id) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid=" + place_id);
        url.append("&key=" + getResources().getString(R.string.browser_key));
        return url.toString();

    }


    private String getPhotoOfPlace(String photo_reference, int maxWidth) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth=" + maxWidth);
        url.append("&photoreference" + photo_reference);
        url.append("&key=" + getResources().getString(R.string.browser_key));
        return url.toString();
    }
}
