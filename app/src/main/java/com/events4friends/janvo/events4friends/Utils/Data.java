package com.events4friends.janvo.events4friends.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

public class Data extends Event{

    private ArrayList<Event> eventList;

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public Data(Boolean empty){

        this.eventList = new ArrayList<>();

        if(empty) {
            //leere Liste
        }else {
            createEventList();
        }
    }

    private void createEventList(){

        //Event 1
        int id1 = 1;
        String name1 = "Privatparty 1";
        String description1 = "Diese Party wird der Hammer!";
        Date date1 = new Date(12);
        LatLng position1 = new LatLng(48.0396805,9.3257261 );

        Event event1 = new Event(id1, name1, description1, date1, position1);

        //Event 2
        Event event2 = new Event(2, "Privatparty 2", description1, date1, new LatLng(48.0390492, 9.3270052));

        //Event 3
        Event event3 = new Event(3, "Privatparty 3", description1, date1, null);

        //Event 4
        Event event4 = new Event(4, "Privatparty 4", description1, date1, null);

        //Event 5
        Event event5 = new Event(5, "Privatparty 5", description1, date1, null);

        //Event 6
        Event event6 = new Event(6, "Privatparty 6", description1, date1, null);

        //Event 7
        Event event7 = new Event(7, "Privatparty 7", description1, date1, null);

        //Event 8
        Event event8 = new Event(8, "Privatparty 8", description1, date1, null);

        //Event 9
        Event event9 = new Event(9, "Privatparty 9", description1, date1, null);

        //Event 10
        Event event10 = new Event(10, "Privatparty 10", description1, date1, null);


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
