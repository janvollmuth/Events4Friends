package com.events4friends.janvo.events4friends.Utils;

import android.graphics.Bitmap;

import com.events4friends.janvo.events4friends.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;
    private LatLng position;
    private String address;
    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPosition(LatLng latLng) {
        this.position = latLng;
    }

    public String getAddress() {
        return address;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
    }

    public Event(){

    }

    public Event(int id, String name, String description, Date date, String address, Bitmap bitmap) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.image = bitmap;
    }

    public Event(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
