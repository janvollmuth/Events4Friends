package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Data;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private static final String TAG = "MapActivity";
    private static final int ACTIVITY_NUM = 1;

    //Minimale Zeit ab der die GPS Koordinaten aktualisiert werden
    private static final long MIN_TIME_TO_REFRESH = 3000L;

    //Minimale Distanz ab der die GPS Koordinaten aktualisiert werden
    private static final float MIN_DISTANCE_TO_REFRESH = 0F;

    private Context mContext = MapActivity.this;
    private LocationManager locationManager;
    private LatLng myposition = new LatLng(48.0353709, 9.3265154);
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        eventList = Data.getEventList();

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

        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            int eventId = checkOrigin(intent);

            for(int i = 0; i < eventList.size(); i++) {

                if(eventList.get(i).getId() == eventId) {

                    LatLng position = eventList.get(i).getPosition();
                    googleMap.addMarker(new MarkerOptions().position(position)
                            .title(eventList.get(i).getName()));

                    googleMap.setMinZoomPreference(16.0f);
                    googleMap.setMaxZoomPreference(16.0f);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

                }

            }

        }else {

            for(int i = 0; i < eventList.size(); i++) {

                if(eventList.get(i).getPosition() != null) {

                    LatLng position = eventList.get(i).getPosition();
                    googleMap.addMarker(new MarkerOptions().position(position)
                            .title(eventList.get(i).getName()));

                }
            }

        /*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("request location");
            return;
        }

        System.out.println("request location");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_TO_REFRESH, MIN_DISTANCE_TO_REFRESH, MapActivity.this);*/

            googleMap.setMinZoomPreference(12.0f);
            googleMap.setMaxZoomPreference(16.0f);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));

        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location != null) {
            myposition = new LatLng(location.getLatitude(), location.getLongitude());
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(MapActivity.this, "GPS ist aktiviert", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String provider) {

        Toast.makeText(MapActivity.this, "GPS ist deaktiviert", Toast.LENGTH_LONG).show();

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case Manifest.permission.ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }


    }*/

    public Integer checkOrigin(Intent intent) {

        int id = intent.getIntExtra("EventId", 0);
        Log.d("myLog", "Id: " + id);

        return id;

    }

}
