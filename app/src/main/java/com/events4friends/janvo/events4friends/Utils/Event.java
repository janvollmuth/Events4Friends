package com.events4friends.janvo.events4friends.Utils;

import java.util.Date;

public class Event {

    private int id;
    private String name;
    private String description;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date value) {
        date = value;
    }

    public Event(int id, String name, String description, Date date) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
