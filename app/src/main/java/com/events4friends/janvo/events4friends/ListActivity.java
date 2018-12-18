package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.EventList;

import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private ListView listView;
    private Context mContext = ListActivity.this;
    private Toolbar toolbar;
    private EventList eventList;
    private int[] images = {R.drawable.test_event_image};
    private EventList newEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupBottomNavigationView();
        setupListView();
        setupToolbar();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupListView() {


        newEventList = new EventList();
        eventList = new EventList();

        newEventList = eventList;


        listView = findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
    }

    private void setupToolbar() {

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(newText != null && !newText.isEmpty()) {

                    for(int i = 0; i < newEventList.getEventList().size(); i++) {

                        if(newEventList.getEventList().get(i).getName().toLowerCase().contains(newText)) {

                            newEventList.getEventList().add(newEventList.getEventList().get(i));
                        }
                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    //ArrayAdapter arrayAdapter = new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_1, newList);
                    listView.setAdapter(customAdapter);
                }
                else {

                    //wenn der Eingabetext leer ist, wird die komplette liste angezeigt
                    CustomAdapter customAdapter = new CustomAdapter();
                    newEventList = eventList;
                    //ArrayAdapter arrayAdapter = new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(customAdapter);
                }
                return true;
            }
        });

        toolbar = findViewById(R.id.toolbar);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newEventList.getEventList().size();
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

            imageView.setImageResource(images[0]);
            textview_name.setText(newEventList.getEventList().get(position).getName());
            textview_description.setText(newEventList.getEventList().get(position).getDescription());

            return convertView;
        }
    }

}
