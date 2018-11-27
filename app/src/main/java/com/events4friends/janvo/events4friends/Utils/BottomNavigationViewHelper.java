package com.events4friends.janvo.events4friends.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.events4friends.janvo.events4friends.ListActivity;
import com.events4friends.janvo.events4friends.MapActivity;
import com.events4friends.janvo.events4friends.R;
import com.events4friends.janvo.events4friends.SettingsActivity;

public class BottomNavigationViewHelper {

    public static void enableNavigation(final Context context, BottomNavigationView view) {

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.navigation_list:

                        Intent intent1 = new Intent(context, ListActivity.class); //ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.navigation_map:

                        Intent intent2 = new Intent(context, MapActivity.class); //ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.navigation_settings:

                        Intent intent3 = new Intent(context, SettingsActivity.class); //ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                }

                return false;
            }
        });

    }

}
