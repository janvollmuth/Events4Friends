package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Data;
import com.events4friends.janvo.events4friends.Utils.Event;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = SettingsActivity.this;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        eventList = Data.getEventList();

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
