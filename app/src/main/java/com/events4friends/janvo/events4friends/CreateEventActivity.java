package com.events4friends.janvo.events4friends;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.events4friends.janvo.events4friends.Utils.Event;
import com.events4friends.janvo.events4friends.Utils.FireDBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {

    private Context mContext = CreateEventActivity.this;

    //Variables
    private Uri imageUri;
    private final int requestCode = 1;
    private Intent intent;
    private Bitmap bitmap;
    private Event newEvent;
    private Calendar calendar;
    private Time time;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int tmpDay, tmpMonth, tmpYear;
    private int tmpHour, tmpMinutes;

    //User-Interface
    private ImageButton image;
    private EditText name;
    private EditText description;
    private Button dateButton;
    private Button timeButton;
    private EditText address;
    private Button addEvent;

    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Init firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //Init view
        image = findViewById(R.id.newevent_image);
        name = findViewById(R.id.newevent_name);
        description = findViewById(R.id.newevent_description);
        address = findViewById(R.id.newevent_address);
        dateButton = findViewById(R.id.newevent_date);
        timeButton = findViewById(R.id.newevent_time);
        addEvent = findViewById(R.id.addevent);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, requestCode);

            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateButton.setText(dayOfMonth + "." + month + "." + year);
                        tmpDay = dayOfMonth;
                        tmpMonth = month;
                        tmpYear = year;
                    }
                }, year, month, day);

                datePickerDialog.show();

            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                boolean hourView = true;

                timePickerDialog = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeButton.setText(hourOfDay + ":" + minute);
                        tmpHour = hourOfDay;
                        tmpMinutes = minute;
                    }
                }, hour, minutes, hourView);

                timePickerDialog.show();
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Geocoder geocoder = new Geocoder(CreateEventActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(address.getText().toString(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("myLog", "Fehler Geocoding");
                    Toast.makeText(CreateEventActivity.this, "Falsche Adresse! Bitte neue eingeben!", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("myLog", "Uhrzeit:" + timeButton.getText());
                newEvent = new Event(FireDBHelper.getEventList().size()+1, name.getText().toString(), description.getText().toString(), address.getText().toString(), String.valueOf(tmpHour),
                        String.valueOf(tmpMinutes), String.valueOf(tmpDay), String.valueOf(tmpMonth), String.valueOf(tmpYear));

                if(!newEvent.getName().isEmpty() && !newEvent.getDescription().isEmpty() && !newEvent.getAddress().isEmpty() && !newEvent.getHour().isEmpty()
                        && !newEvent.getMinutes().isEmpty() && !newEvent.getDay().isEmpty() && !newEvent.getMonth().isEmpty() && !newEvent.getYear().isEmpty()) {

                    FireDBHelper.writeEventToDatabase(newEvent);
                    //uploadImage();

                    Intent intent = new Intent(mContext, ListActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CreateEventActivity.this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void uploadImage() {

        if(imageUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(("images/" + newEvent.getName()));
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateEventActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateEventActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0+taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress+"%");
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {

            if(this.requestCode == requestCode) {

                imageUri = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    image.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
