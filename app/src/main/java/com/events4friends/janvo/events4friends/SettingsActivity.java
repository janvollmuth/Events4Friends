package com.events4friends.janvo.events4friends;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Event;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = SettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupBottomNavigationView();

        Button signoutButton = findViewById(R.id.signout_button);

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                Toast.makeText(SettingsActivity.this, "Sie haben sich erfolgreich abgemeldet", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
