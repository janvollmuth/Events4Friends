package com.events4friends.janvo.events4friends.Utils;

import android.graphics.Bitmap;

import com.events4friends.janvo.events4friends.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String description;
    private String hour;
    private String minutes;
    private String day;
    private String month;
    private String year;
    private LatLng position;
    private String address;

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

    public void setDescription(String description) {
        this.description = description;
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

    public void setAddress(String address) {
        this.address = address;
    }


    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Event(){

    }

    public Event(int id, String name, String description, String address, String hour, String minutes, String day, String month, String year) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.hour = hour;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Event(int id, String name) {

        this.id = id;
        this.name = name;
    }
}
