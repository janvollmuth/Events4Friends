package com.events4friends.janvo.events4friends.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class FireDBHelper {

    private  static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    private static ArrayList<Event> eventList = new ArrayList<>();
    private ValueEventListener valueEventListener;

    public static DatabaseReference getDatabaseReference() {
        return myRef;
    }

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

    public FireDBHelper() {


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList = getEventListFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }

    public ArrayList<Event> getEventListFromDatabase(@NonNull DataSnapshot dataSnapshot) {

        long value = dataSnapshot.child("Eventlist").getChildrenCount();
        Log.d("myLog", "Number of children: " + value + " , " + dataSnapshot.child("Eventlist").exists());

        ArrayList<Event> eventList = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.child("Eventlist").getChildren()) {

            HashMap<String, String > map = (HashMap<String, String>) snapshot.getValue();

            Event event = new Event();

            event.setName(map.get("name"));
            event.setId(Integer.parseInt(String.valueOf(map.get("id"))));
            event.setAddress(map.get("address"));
            event.setDescription(map.get("description"));

            Log.d("myLog", "Event: \n" + event.getName() + "\n " + event.getId() + "\n" + event.getAddress() + "\n" + event.getDescription());

            eventList.add(event);
        }

        Log.d("myLog", "Length arraylist: " + eventList.size());

        return eventList;
    }

    public static void writeEventToDatabase(Event event) {

        myRef.child("Eventlist").child(event.getName()).setValue(event);

    }

    public void addUserToDatabase(User user) {
        myRef.child("Userlist").child(user.username).setValue(user);
    }
}
