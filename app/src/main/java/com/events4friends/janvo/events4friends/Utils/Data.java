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
        String name1 = "Weihnachtsfeier Schunk";
        String description1 = "Dies ist eine Platzhalterbeschreibung!";
        Date date1 = new Date(12);
        String address1 = "Hauptstraße 11, 88512 Mengen";
        Bitmap image1 = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.test_event_image);

        Event event1 = new Event(id1, name1, description1, date1, address1, image1);

        //Event 2
        Event event2 = new Event(2, "Jans Homeparty", description1, date1, "Weithartstraße 14, 88512 Mengen", image1);

        //Event 3
        Event event3 = new Event(3, "Alex Geburtstag", description1, date1, "Krauchenwieser Str. 48, 88512 Mengen", image1);

        //Event 4
        Event event4 = new Event(4, "Martins kleine Feier", description1, date1, "Auf dem Hof 7, 88512 Mengen ", image1);

        //Event 5
        Event event5 = new Event(5, "Tobias Abschlussparty", description1, date1, "Meßkircher Str. 3, 88512 Mengen", image1);

        //Event 6
        Event event6 = new Event(6, "Überraschungsparty Dominik", description1, date1, "Weidenstraße 41, 88512 Mengen ", image1);

        //Event 7
        Event event7 = new Event(7, "Spieleabend bei David", description1, date1, "Hermann-Hesse-Straße 7, 88518 Herbertingen", image1);

        //Event 8
        Event event8 = new Event(8, "Fasnetsopening Mengen", description1, date1, "Meßkircher Str. 20, 88512 Mengen", image1);

        //Event 9
        Event event9 = new Event(9, "Nadjas Heimkehr", description1, date1, "Beizkofer Str. 13, 88512 Mengen", image1);

        //Event 10
        Event event10 = new Event(10, "Simons Einweihungsparty", description1, date1, "Weithartstraße 8, 88512 Mengen", image1);


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
