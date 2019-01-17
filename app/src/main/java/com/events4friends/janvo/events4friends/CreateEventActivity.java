package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.events4friends.janvo.events4friends.Utils.Data;
import com.events4friends.janvo.events4friends.Utils.Event;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {

    private Context mContext = CreateEventActivity.this;
    private ImageButton image;
    private EditText name;
    private EditText description;
    private EditText date;
    private EditText time;
    private EditText address;
    private Intent intent;
    private final int requestCode = 1;
    private Uri imageUri;
    private Bitmap bitmap;
    private Button addEvent;
    private Event newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        image = findViewById(R.id.newevent_image);
        name = findViewById(R.id.newevent_name);
        description = findViewById(R.id.newevent_description);
        address = findViewById(R.id.newevent_address);
        date = findViewById(R.id.newevent_date);
        time = findViewById(R.id.newevent_time);
        addEvent = findViewById(R.id.addevent);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, requestCode);

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
                    return;
                }

                newEvent = new Event(Data.getEventList().size()+1, name.getText().toString(), description.getText().toString(), new Date(12), address.getText().toString(), bitmap);

                Log.d("myLog", "Event-Array (CreateEventActivity) " + Data.getEventList().size());
                Data.getEventList().add(newEvent);
                Log.d("myLog", "Event-Array (CreateEventActivity) " + Data.getEventList().size());
                Intent intent = new Intent(mContext, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK) {

            if(this.requestCode == requestCode) {

                imageUri = data.getData();
                try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {

                    bitmap = BitmapFactory.decodeStream(inputStream);
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
