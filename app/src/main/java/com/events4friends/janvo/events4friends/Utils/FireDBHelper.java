package com.events4friends.janvo.events4friends.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.ListActivity;
import com.events4friends.janvo.events4friends.LoginActivity;
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
    private static ArrayList<User> userList = new ArrayList<>();
    private ValueEventListener valueEventListener;
    private Boolean checkLogin = false;
    private Boolean addUser = false;
    private User user;

    public static DatabaseReference getDatabaseReference() {
        return myRef;
    }

    public static ArrayList<Event> getEventListFromDatabase() {
        return eventList;
    }

    public static ArrayList<User> getUserListFromDatabase() {
        return userList;
    }

    public FireDBHelper() {

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList = syncEventListFromDatabase(dataSnapshot);
                userList = syncUserListFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }

    private ArrayList<Event> syncEventListFromDatabase(@NonNull DataSnapshot dataSnapshot) {

        ArrayList<Event> eventList = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.child("Eventlist").getChildren()) {
            HashMap<String, String > map = (HashMap<String, String>) snapshot.getValue();
            Event event = new Event();
            event.setName(map.get("name"));
            event.setId(Integer.parseInt(String.valueOf(map.get("id"))));
            event.setAddress(map.get("address"));
            event.setDescription(map.get("description"));
            eventList.add(event);
        }

        return eventList;
    }

    private ArrayList<User> syncUserListFromDatabase(@NonNull DataSnapshot dataSnaphot) {

        ArrayList<User> userList = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnaphot.child("Eventlist").getChildren()) {
            HashMap<String, String > map = (HashMap<String, String>) snapshot.getValue();
            User user = new User();
            user.setUsername(map.get("username"));
            user.setPassword(map.get("password"));
            userList.add(user);
        }

        return userList;
    }

    public boolean checkLogin(User newUser) {

        this.user = newUser;

        myRef.child("Userlist").child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User databaseUser = dataSnapshot.getValue(User.class);

                Log.d("myLog", "User: " + databaseUser.getUsername());
                Log.d("myLog", "User: " + databaseUser.getPassword());

                if(databaseUser != null){
                    if(user.getUsername().equals(databaseUser.getUsername()) && user.getPassword().equals(databaseUser.getPassword())) {
                        checkLogin = true;
                        Log.d("myLog", "Check: " + checkLogin);
                    }
                    else {
                        checkLogin = false;
                    }
                }
                else {
                        checkLogin = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return checkLogin;
    }

    public void writeEventToDatabase(Event event) {

        myRef.child("Eventlist").child(event.getName()).setValue(event);

    }

    public boolean addUserToDatabase(User newUser) {

        this.user = newUser;
        myRef.child("Userlist").child(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User databaseUser = dataSnapshot.getValue(User.class);
                if (databaseUser == null) {
                    myRef.child("Userlist").child(user.getUsername()).setValue(user);
                    addUser = true;
                } else {
                    addUser = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return addUser;
    }
}
