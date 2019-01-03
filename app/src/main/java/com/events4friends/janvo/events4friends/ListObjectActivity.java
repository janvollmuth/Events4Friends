package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Data;
import com.events4friends.janvo.events4friends.Utils.Event;

import java.util.ArrayList;

public class ListObjectActivity extends AppCompatActivity {

    private Context mContext = ListObjectActivity.this;
    private static final int ACTIVITY_NUM = 0;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_object);

        Intent intent = getIntent();
        int id = intent.getFlags();

        System.out.println(id);

        prepareEventView(id);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void prepareEventView(int id){

        System.out.println("Prepare...");

        eventList = Data.getEventList();

        ImageView imageView = findViewById(R.id.listobject_image);
        TextView textview_name = findViewById(R.id.listobject_title);
        TextView textview_description = findViewById(R.id.listobject_description);
        TextView textview_location = findViewById(R.id.listobject_location_text);
        TextView textView_time = findViewById(R.id.listobject_time_text);


        for(int i = 0; i < eventList.size(); i++) {
            System.out.println("Check: " + i + " ID: " + eventList.get(i).getId() + " IDClicked: " + id);
            if(eventList.get(i).getId()== id) {
                imageView.setImageResource(eventList.get(i).getImage());
                textview_name.setText(eventList.get(i).getName());
                textview_description.setText(eventList.get(i).getDescription());
                textview_location.setText("Mengen");
                //textView_time.setText((String) data.getEventList().get(i).getDate());
            }
        }

    }

}
