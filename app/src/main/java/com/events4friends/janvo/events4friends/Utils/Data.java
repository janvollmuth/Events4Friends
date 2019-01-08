package com.events4friends.janvo.events4friends.Utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.events4friends.janvo.events4friends.R;

import java.util.ArrayList;
import java.util.Date;

public class Data extends Event{

    private static ArrayList<Event> eventList;

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

    public Data(){

        eventList = new ArrayList<>();
        createEventList();
    }

    private void createEventList(){

        //Event 1
        int id1 = 1;
        String name1 = "Privatparty 1";
        String description1 = "Diese Party wird der Hammer!";
        Date date1 = new Date(12);
        String address1 = "Lehmgrubenstraße 12 Mengen";
        Bitmap image1 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.test_event_image);

        Event event1 = new Event(id1, name1, description1, date1, address1, image1);

        //Event 2
        Event event2 = new Event(2, "Jans Homeparty", description1, date1, "Weithartstraße 14 Mengen", image1);

        //Event 3
        Event event3 = new Event(3, "Privatparty 3", description1, date1, null, image1);

        //Event 4
        Event event4 = new Event(4, "Privatparty 4", description1, date1, null, image1);

        //Event 5
        Event event5 = new Event(5, "Privatparty 5", description1, date1, null, image1);

        //Event 6
        Event event6 = new Event(6, "Privatparty 6", description1, date1, null, image1);

        //Event 7
        Event event7 = new Event(7, "Privatparty 7", description1, date1, null, image1);

        //Event 8
        Event event8 = new Event(8, "Privatparty 8", description1, date1, null, image1);

        //Event 9
        Event event9 = new Event(9, "Privatparty 9", description1, date1, null, image1);

        //Event 10
        Event event10 = new Event(10, "Privatparty 10", description1, date1, null, image1);


        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        eventList.add(event4);
        eventList.add(event5);
        eventList.add(event6);
        eventList.add(event7);
        eventList.add(event8);
        eventList.add(event9);
        eventList.add(event10);

    }


}
