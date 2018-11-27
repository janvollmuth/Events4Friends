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

import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private ListView listView;
    private Context mContext = ListActivity.this;
    private Toolbar toolbar;
    private ArrayList<Event> eventList;
    private int[] images = {R.drawable.test_event_image};
    private ArrayList<Event> newEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupBottomNavigationView();
        setupListView();
        setupToolbar();
    }

    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupListView() {


        //Event 1
        int id1 = 1;
        String name1 = new String("Privatparty");
        String description1 = new String("Diese Party wird der Hammer!");
        Date date1 = new Date(12);

        Event event1 = new Event(id1, name1, description1, date1);

        //Event 2


        newEventList = new ArrayList<>();
        eventList = new ArrayList<>();
        eventList.add(event1);

        newEventList = eventList;


        listView = (ListView) findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
    }

    private void setupToolbar() {

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(newText != null && !newText.isEmpty()) {

                    for(int i = 0; i < newEventList.size(); i++) {

                        if(newEventList.get(i).getName().toLowerCase().contains(newText)) {

                            newEventList.add(newEventList.get(i));
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newEventList.size();
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

            ImageView imageView = (ImageView) convertView.findViewById(R.id.event_image);
            TextView textview_name = (TextView) convertView.findViewById(R.id.event_name);
            TextView textview_description = (TextView) convertView.findViewById(R.id.event_description);

            imageView.setImageResource(images[0]);
            textview_name.setText(newEventList.get(position).getName());
            textview_description.setText(newEventList.get(position).getDescription());

            return convertView;
        }
    }

}
