package com.events4friends.janvo.events4friends.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

public class FireDBHelper {

    private static final String NAME_KEY = "event";
    private static final String EVENT_KEY = "name";
    private DocumentReference docRef = FirebaseFirestore.getInstance().document("Events");

    public FireDBHelper() {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

    }

    public void saveEvent(String event, String name) {

        if(event.isEmpty() || name.isEmpty()) { return; }

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(EVENT_KEY, event);
        dataToSave.put(NAME_KEY, name);
        docRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("myLog", "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("myLog", "Document was not saved", e);
            }
        });

    }

}
