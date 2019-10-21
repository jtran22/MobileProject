package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class IndividualEventPage extends AppCompatActivity {
    private String eventName;
    private String eventAddress;
    private String eventDate;
    private String eventTime;
    private String eventDistance;
    private String eventDetails;
    private String eventImgRef;
    private String eventId;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_event_page);
        Intent intent = getIntent();
        eventName = intent.getStringExtra("eventName");
        eventAddress = intent.getStringExtra("eventAddress");
        eventDate = intent.getStringExtra("eventDate");
        eventTime = intent.getStringExtra("eventTime");
        eventDistance = intent.getStringExtra("eventDistance");
        eventDetails = intent.getStringExtra("eventDetails");
        eventImgRef = intent.getStringExtra("imageRef");
        eventId = intent.getStringExtra("eventId");

        TextView tvEventName= findViewById(R.id.tvEventName);
        TextView tvEventAddress= findViewById(R.id.tvEventAddress);
        TextView tvEventDate= findViewById(R.id.tvEventDate);
        TextView tvEventTime= findViewById(R.id.tvEventTime);
        TextView tvEventDistance= findViewById(R.id.tvEventDistance);
        TextView tvEventDetails= findViewById(R.id.tvEventDetails);
        ImageView imEvent = findViewById(R.id.ivEventImage);

        tvEventName.setText(eventName);
        tvEventAddress.setText(eventAddress);
        tvEventDate.setText(eventDate);
        tvEventTime.setText(eventTime);
        tvEventDistance.setText(eventDistance);
        tvEventDetails.setText(eventDetails);

        StorageReference imageRef = storage.getReferenceFromUrl(eventImgRef);
        Glide.with(this)
                .asBitmap()
                .load(imageRef)
                .into(imEvent);

        Log.d("eventInfo", eventId + " " + eventName + " " + eventAddress + " " + eventDate + " " + eventTime+ " " +eventDistance+ " " +eventDetails+ " " +eventImgRef);
    }
}
