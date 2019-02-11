package com.events4friends.janvo.events4friends;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Variables
    private static final String TAG = "ListActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = ListActivity.this;
    private ArrayList<Event> eventList;
    private ArrayList<Event> newEventList;
    private boolean listDefault;

    //User-Interface
    private ListView listView;
    private SearchView searchView;
    private FloatingActionButton addEventButton;

    //Firebase
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupBottomNavigationView();
        setupListView();
        setupAddEventButton();
        setupCoordinates();
        setupSearchView();
    }

    private void setupCoordinates() {

        Log.d(TAG, "setup Coordinates");
        Geocoder geocoder = new Geocoder(ListActivity.this, Locale.getDefault());

        for(int i = 0; i < FireDBHelper.getEventList().size(); i++) {

            if(FireDBHelper.getEventList().get(i).getAddress() != null && FireDBHelper.getEventList().get(i).getPosition() == null) {
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(FireDBHelper.getEventList().get(i).getAddress(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Fehler Geocoding");
                }

                Address address = null;

                if(addresses == null) {
                    return;
                }
                if (!addresses.isEmpty()) {
                    address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    FireDBHelper.getEventList().get(i).setPosition(latLng);
                }
                else {
                    Log.d(TAG, "keine Koordianten gefunden");
                }

                Log.d(TAG, "Coordinates: " + FireDBHelper.getEventList().get(i).getPosition());
            }

        }
    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupListView() {

        listDefault = true;

        Log.d(TAG, "Event-Array (ListActivity) " + FireDBHelper.getEventList().size());
        eventList = FireDBHelper.getEventList();
        Log.d(TAG, "Event-Array (ListActivity) " + FireDBHelper.getEventList().size());
        newEventList = new ArrayList<>();

        listView = findViewById(R.id.listview);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(this);
    }

    private void setupSearchView() {

        searchView = findViewById(R.id.searchView);

        searchView.setQueryHint("Type in the Event you searching for");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                if(newText == null && newText.isEmpty()) {

                    listDefault = true;

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newEventList = new ArrayList<>();

                if(newText != null && !newText.isEmpty()) {

                    listDefault = false;

                    for(int i = 0; i < FireDBHelper.getEventList().size(); i++) {
                        //Log.d(TAG, "Test Liste: " + (eventList.get(i).getName().contains(newText) || eventList.get(i).getName().contains(newText)));
                        if((eventList.get(i).getName().contains(newText) || eventList.get(i).getName().toLowerCase().contains(newText) || eventList.get(i).getName().toUpperCase().contains(newText) ) /*&& !newEventList.contains(eventList.get(i))*/) {
                            newEventList.add(FireDBHelper.getEventList().get(i));
                        }
                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);

                } else {

                    int i = 0;
                    listDefault = true;

                    while(newEventList.size() != 0) {
                        newEventList.remove(i);

                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                }
                return true;
            }
        });
    }

    public void setupAddEventButton() {

        addEventButton = findViewById(R.id.addevent_button);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, CreateEventActivity.class);

                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(mContext, ListObjectActivity.class);

        if(newEventList.size() != 0){
            Event event = newEventList.get(position);
            String eventName = event.getName();
            intent.putExtra("Eventname", eventName);
        }else {
            Event event = eventList.get(position);
            String eventName = event.getName();
            intent.putExtra("Eventname", eventName);
        }

        mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            if(newEventList.size() == 0 && listDefault) {
                return FireDBHelper.getEventList().size();
            }else if(newEventList.size() == 0 && !listDefault){
                return newEventList.size();
            }else {
                return newEventList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.listview_event, null);

            ImageView imageView = convertView.findViewById(R.id.event_image);
            TextView textview_name = convertView.findViewById(R.id.event_name);
            TextView textview_description = convertView.findViewById(R.id.event_description);

            if(newEventList.size() == 0) {

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.test_event_image));
                textview_name.setText(eventList.get(position).getName());
                textview_description.setText(eventList.get(position).getDescription());

            }else {

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.test_event_image));
                textview_name.setText(newEventList.get(position).getName());
                textview_description.setText(newEventList.get(position).getDescription());
            }
            return convertView;
        }
    }

    private File getImageFromStorage(String name) {

        StorageReference imageReference = storageReference.child("images/" + name);
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "*");

            imageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("myLog", "Download erfolgreich");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d(TAG, String.valueOf(localFile.getAbsolutePath()));

        return localFile;
    }
}
