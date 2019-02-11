package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static java.lang.String.valueOf;

public class ListObjectActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext = ListObjectActivity.this;
    private static final int ACTIVITY_NUM = 0;

    //Variables
    private DatabaseReference myRef = FireDBHelper.getDatabaseReference();
    private String name;

    //User-Interface
    private ImageView imageView;
    private TextView textview_name;
    private TextView textview_description;
    private TextView textview_location;
    private TextView textview_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_object);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Eventname");

        Button showOnMap = findViewById(R.id.button_showOnMap);
        showOnMap.setOnClickListener(this);

        prepareEventView(name);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void prepareEventView(String name){

        this.name = name;

        imageView = findViewById(R.id.listobject_image);
        textview_name = findViewById(R.id.listobject_title);
        textview_description = findViewById(R.id.listobject_description);
        textview_location = findViewById(R.id.listobject_location_text);
        textview_time = findViewById(R.id.listobject_time_text);

        myRef.child("Eventlist").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                String datetimeString = String.format("%02d:%02d , %02d.%02d.%04d", Integer.parseInt(event.getHour()),
                        Integer.parseInt(event.getMinutes()), Integer.parseInt(event.getDay()), Integer.parseInt(event.getMonth()), Integer.parseInt(event.getYear()));

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.test_event_image));
                textview_name.setText(event.getName());
                textview_description.setText(event.getDescription());
                textview_location.setText(event.getAddress());
                textview_time.setText(datetimeString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, MapActivity.class);
        intent.putExtra("Eventname", name);
        mContext.startActivity(intent);
    }
}
