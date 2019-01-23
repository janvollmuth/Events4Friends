package com.events4friends.janvo.events4friends;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Data;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private ListView listView;
    private Context mContext = ListActivity.this;
    private ArrayList<Event> eventList;
    private ArrayList<Event> newEventList;
    private boolean listDefault;
    private SearchView searchView;
    //private LatLng myposition = new LatLng(48.0353709, 9.3265154);
    private FloatingActionButton addEventButton;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Erzeuge ListActivity...");

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);
        setContentView(R.layout.test_layout);
        setupFireDB();

        /*setupBottomNavigationView();
        setupListView();
        setupAddEventButton();
        setupCoordinates();
        setupSearchView();*/
    }

    private void setupCoordinates() {

        Log.d("myLog", "setup Coordinates");

        Geocoder geocoder = new Geocoder(ListActivity.this, Locale.getDefault());

        for(int i = 0; i < Data.getEventList().size(); i++) {

            if(Data.getEventList().get(i).getAddress() != null && Data.getEventList().get(i).getPosition() == null) {
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(Data.getEventList().get(i).getAddress(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address address = null;
                if (addresses != null) {
                    address = addresses.get(0);
                }
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                Data.getEventList().get(i).setPosition(latLng);
                Log.d("myLog", "Coordinates: " + Data.getEventList().get(i).getPosition());
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

        //old version for data
        listDefault = true;
        if(Data.getEventList() == null) {
            new Data();
        }
        Log.d("myLog", "Event-Array (ListActivity) " + Data.getEventList().size());
        eventList = Data.getEventList();
        Log.d("myLog", "Event-Array (ListActivity) " + Data.getEventList().size());
        newEventList = new ArrayList<>();


        //new version for data with SQLite


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

                if(newText != null && !newText.isEmpty()) {

                    listDefault = false;

                    for(int i = 0; i < Data.getEventList().size(); i++) {

                        //System.out.println(newText + " " + data.getEventList().get(i).getName().toLowerCase().contains(newText));

                        if(eventList.get(i).getName().toLowerCase().contains(newText) && !newEventList.contains(eventList.get(i))) {

                            newEventList.add(Data.getEventList().get(i));
                        }
                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                    //showLists();

                } else {

                    //wenn der Eingabetext leer ist, wird die komplette liste angezeigt

                    int i = 0;
                    listDefault = true;

                    while(newEventList.size() != 0) {

                        //System.out.println("Size:" + newData.getEventList().size() + "Removing: " + newData.getEventList().get(i).getName());
                        newEventList.remove(i);

                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                }
                //showLists();
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
            int eventId = event.getId();
            intent.addFlags(eventId);
        }else {
            Event event = eventList.get(position);
            int eventId = event.getId();
            intent.addFlags(eventId);
        }

        mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            if(newEventList.size() == 0 && listDefault) {
                return Data.getEventList().size();
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

                imageView.setImageBitmap(eventList.get(position).getImage());
                textview_name.setText(eventList.get(position).getName());
                textview_description.setText(eventList.get(position).getDescription());

            }else {

                imageView.setImageBitmap(newEventList.get(position).getImage());
                textview_name.setText(newEventList.get(position).getName());
                textview_description.setText(newEventList.get(position).getDescription());
            }
            return convertView;
        }
    }

    private void showLists() {

        for(int i = 0; i < eventList.size(); i++) {
            System.out.println("eventList " + eventList.get(i).getName());
        }

        for(int i = 0; i < newEventList.size(); i++) {
            System.out.println("newEventList " + newEventList.get(i).getName());
        }

        if(newEventList.size() == 0) {
            System.out.println("Liste leer");
        }
    }

    public void setupFireDB() {

        Button add = findViewById(R.id.test_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireDBHelper dbHelper = new FireDBHelper();

                EditText event = findViewById(R.id.test_event);
                EditText name = findViewById(R.id.test_name);

                String e = event.getText().toString();
                String n = name.getText().toString();

                dbHelper.saveEvent(e, n);

            }
        });

    }

}
