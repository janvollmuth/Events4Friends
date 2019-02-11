package com.events4friends.janvo.events4friends;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private static final String TAG = "MapActivity";
    private static final int ACTIVITY_NUM = 1;
    //Minimale Zeit ab der die GPS Koordinaten aktualisiert werden
    private static final long MIN_TIME_TO_REFRESH = 3000L;
    //Minimale Distanz ab der die GPS Koordinaten aktualisiert werden
    private static final float MIN_DISTANCE_TO_REFRESH = 0F;
    private static final int MY_LOCATION_REQUEST_CODE = 1;
    private Context mContext = MapActivity.this;

    //Variables
    private GoogleMap map;
    private LatLng myposition;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        eventList = FireDBHelper.getEventList();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        Intent intent = getIntent();
        myposition = eventList.get(0).getPosition();

        setupLocation();

        //For ShowOnMap-View
        if(intent.getExtras() != null) {
            String eventname = checkOrigin(intent);

            for(int i = 0; i < eventList.size(); i++) {

                if(eventList.get(i).getName().contains(eventname)) {

                    LatLng position = eventList.get(i).getPosition();
                    googleMap.addMarker(new MarkerOptions().position(position)
                            .title(eventList.get(i).getName()));

                    googleMap.setMinZoomPreference(16.0f);
                    googleMap.setMaxZoomPreference(20.0f);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                }
            }
        }
        else {
            for (int i = 0; i < eventList.size(); i++) {

                if (eventList.get(i).getPosition() != null) {

                    LatLng position = eventList.get(i).getPosition();
                    googleMap.addMarker(new MarkerOptions().position(position)
                            .title(eventList.get(i).getName()));

                }
            }
            googleMap.setMinZoomPreference(12.0f);
            googleMap.setMaxZoomPreference(16.0f);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));
        }
    }


    public String checkOrigin(Intent intent) {

        String name = intent.getStringExtra("Eventname");
        Log.d(TAG, "Eventname: " + name);
        return name;
    }

    private void setupLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            Log.d("myLog", "Location enabled");
        } else {
            // Show rationale and request permission.
        }
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("myLog", "Permissionresult");
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "MyLocation button clicked, but false", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
}
