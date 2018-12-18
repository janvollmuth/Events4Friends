package com.events4friends.janvo.events4friends.Utils;

import java.util.ArrayList;
import java.util.Date;

public class EventList extends Event{

    private ArrayList<Event> eventList;

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public EventList(){

        this.eventList = new ArrayList<>();

        createEventList();

    }

    private void createEventList(){

        //Event 1
        int id1 = 1;
        String name1 = "Privatparty";
        String description1 = "Diese Party wird der Hammer!";
        Date date1 = new Date(12);

        Event event1 = new Event(id1, name1, description1, date1);

        //Event 2
        Event event2 = new Event(id1, name1, description1, date1);

        //Event 3
        Event event3 = new Event(id1, name1, description1, date1);

        //Event 4
        Event event4 = new Event(id1, name1, description1, date1);

        //Event 5
        Event event5 = new Event(id1, name1, description1, date1);

        //Event 6
        Event event6 = new Event(id1, name1, description1, date1);

        //Event 7
        Event event7 = new Event(id1, name1, description1, date1);

        //Event 8
        Event event8 = new Event(id1, name1, description1, date1);

        //Event 9
        Event event9 = new Event(id1, name1, description1, date1);

        //Event 10
        Event event10 = new Event(id1, name1, description1, date1);


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
