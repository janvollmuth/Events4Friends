package com.events4friends.janvo.events4friends.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;
    private LatLng position;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public LatLng getPosition() {
        return position;
    }

    public Event(){

    }

    public Event(int id, String name, String description, Date date, LatLng position) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.position = position;
    }
}
