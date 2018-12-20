package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.events4friends.janvo.events4friends.Utils.BottomNavigationViewHelper;
import com.events4friends.janvo.events4friends.Utils.Data;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;

    private ListView listView;
    private Context mContext = ListActivity.this;
    private Data data;
    private int[] images = {R.drawable.test_event_image};
    private Data newData;
    private boolean listDefault;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Erzeuge ListActivity...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupBottomNavigationView();
        setupListView();
        setupSearchView();
    }

    private void setupBottomNavigationView() {

        System.out.println("Initialisiere die Navigation Bar...");

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupListView() {

        System.out.println("Bereite die Eventliste vor...");

        listDefault = true;

        data = new Data(false);

        System.out.println(data.getEventList());

        newData = new Data(true);

        System.out.println(newData.getEventList());

        listView = findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
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

                    for(int i = 0; i < data.getEventList().size(); i++) {

                        //System.out.println(newText + " " + data.getEventList().get(i).getName().toLowerCase().contains(newText));

                        if(data.getEventList().get(i).getName().toLowerCase().contains(newText) && !newData.getEventList().contains(data.getEventList().get(i))) {

                            newData.getEventList().add(data.getEventList().get(i));
                        }
                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                    //showLists();

                } else {

                    //wenn der Eingabetext leer ist, wird die komplette liste angezeigt

                    int i = 0;
                    listDefault = true;

                    while(newData.getEventList().size() != 0) {

                        //System.out.println("Size:" + newData.getEventList().size() + "Removing: " + newData.getEventList().get(i).getName());
                        newData.getEventList().remove(i);

                    }

                    CustomAdapter customAdapter = new CustomAdapter();
                    listView.setAdapter(customAdapter);
                    //showLists();
                }
                return true;
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            if(newData.getEventList().size() == 0 && listDefault == true) {
                return data.getEventList().size();
            }else if(newData.getEventList().size() == 0 && listDefault == false){
                return newData.getEventList().size();
            }else {
                return newData.getEventList().size();
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

            //System.out.println(listDefault);

            convertView = getLayoutInflater().inflate(R.layout.listview_event, null);

            ImageView imageView = convertView.findViewById(R.id.event_image);
            TextView textview_name = convertView.findViewById(R.id.event_name);
            TextView textview_description = convertView.findViewById(R.id.event_description);

            if(newData.getEventList().size() == 0) {

                imageView.setImageResource(images[0]);
                textview_name.setText(data.getEventList().get(position).getName());
                textview_description.setText(data.getEventList().get(position).getDescription());

            }else {

                imageView.setImageResource(images[0]);
                textview_name.setText(newData.getEventList().get(position).getName());
                textview_description.setText(newData.getEventList().get(position).getDescription());
            }
            return convertView;
        }
    }

    private void showLists() {

        for(int i = 0; i < data.getEventList().size(); i++) {
            System.out.println("eventList " + data.getEventList().get(i).getName());
        }

        for(int i = 0; i < newData.getEventList().size(); i++) {
            System.out.println("newEventList " + newData.getEventList().get(i).getName());
        }

        if(newData.getEventList().size() == 0) {
            System.out.println("Liste leer");
        }

    }

}
