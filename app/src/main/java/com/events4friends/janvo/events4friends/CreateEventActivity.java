package com.events4friends.janvo.events4friends;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class CreateEventActivity extends AppCompatActivity {

    private Context mContext = CreateEventActivity.this;
    private ImageButton image;
    private EditText description;
    private EditText date;
    private EditText time;
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
        description = findViewById(R.id.newevent_description);
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

        newEvent = new Event(11, "Simons Hausparty", "Die wird hammergeil.", new Date(12), "Weithartstraße 8 Mengen", bitmap);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
