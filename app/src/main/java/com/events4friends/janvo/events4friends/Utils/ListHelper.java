package com.events4friends.janvo.events4friends.Utils;

import java.util.ArrayList;

public class ListHelper {

    public static ArrayList<Event> getLocaleEventList() {
        return eventList;
    }

    public static void setEventList(ArrayList<Event> eventList) {
        ListHelper.eventList = eventList;
    }

    private static ArrayList<Event> eventList;

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        ListHelper.userList = userList;
    }

    private static ArrayList<User> userList;


    public ListHelper() {
        eventList = FireDBHelper.getEventListFromDatabase();
        userList = FireDBHelper.getUserListFromDatabase();
    }
}
