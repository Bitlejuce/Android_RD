package com.shoppinglist.rdproject.sunsetandsunrise;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "AIzaSyC7_-WIqY6A9f9W8-ACvL7g9pkmBe_qbRc";
    private static final String TAG = "MainActivity";
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    public static final int REQUEST_PLACE_PICKER = 102;

    private PlaceDetectionClient mPlaceDetectionClient;

    @BindView(R.id.sunrise)
    TextView sunrise;
    @BindView(R.id.sunset)
    TextView sunset;
    @BindView(R.id.select_loc)
    Button selectLoc;
    @BindView(R.id.current_loc)
    Button currentLoc;
    @BindView(R.id.on_map_btn)
    Button onMapBtn;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.sunrise_sunset)
    TextView sunriseSunset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sunriseSunset.setMovementMethod(LinkMovementMethod.getInstance());
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
    }

    @OnClick(R.id.select_loc)
    public void findPlace(View v) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    @OnClick(R.id.on_map_btn)
    public void onPickButtonClick(View v) {
        // Construct an intent for the place picker
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }

    @OnClick(R.id.current_loc)
    public void onCurrentLocButtonClick(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No permission!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }

        Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                Place place = likelyPlaces.get(0).getPlace();
                bindInfoFields(place);
                likelyPlaces.release();
            }
        });
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                bindInfoFields(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == REQUEST_PLACE_PICKER
                && resultCode == Activity.RESULT_OK) {
            final Place place = PlacePicker.getPlace(data, this);
            bindInfoFields(place);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void bindInfoFields(Place place) {
        String latitude = String.valueOf(place.getLatLng().latitude);
        String longitude = String.valueOf(place.getLatLng().longitude);

        location.setText(place.getAddress());

        getSunRiseAndSetinfoFromApi(latitude, longitude);
    }

    private void getSunRiseAndSetinfoFromApi(String latitude, String longitude) {

        App.getApi().getTimeInfo(latitude, longitude).enqueue(new Callback<TimeInfo>() {
            @Override
            public void onResponse(Call<TimeInfo> call, Response<TimeInfo> response) {
                TimeInfo timeInfo = response.body();
                if (timeInfo != null) {
                    sunrise.setText(timeInfo.getResults().getSunrise());
                    sunset.setText(timeInfo.getResults().getSunset());
                } else {
                    Log.d(TAG, "timeInfo == null ");
                    sunrise.setText("-");
                    sunset.setText("-");
                }
            }

            @Override
            public void onFailure(Call<TimeInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cannot find sunrise/sunset for your location", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

