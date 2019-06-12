package com.itapox.autocompletefragmenttest;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mostraPesquisa(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void mostraPesquisa(boolean set) {
        if (MapsActivity.this.isDestroyed()) return;
        if (!Places.isInitialized()) { Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key)); }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment==null) {
            Log.w(TAG, "autoCompleteFragment is null");
            return;
        } else {
            Log.w(TAG, "autoCompleteFragment.getView(): "+autocompleteFragment.getView());
            Log.w(TAG, "autoCompleteFragment: "+autocompleteFragment);
        }

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.w(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.w(TAG, "Coordinates: "+ place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e(TAG, "AutocompleteSupportFragment - Ocorreu um erro: " + status);
            }
        });
    }

}
