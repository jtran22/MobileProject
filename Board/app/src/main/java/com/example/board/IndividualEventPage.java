package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private String eventUserId;
    private String eventUserName;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference imageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_event_page);
        Intent intent = getIntent();
        eventUserId = intent.getStringExtra("eventUserId");
        eventUserName = intent.getStringExtra("eventUserName");
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
        TextView tvEventPoster = findViewById(R.id.tvEventPoster);
        ImageView imEvent = findViewById(R.id.ivEventImage);
        Button btDeletePost = findViewById(R.id.btDeletePost);
        Button btEditPost = findViewById(R.id.btEditPost);
        if (user.getUid().equals(eventUserId)){
            btEditPost.setVisibility(View.VISIBLE);
        }else btEditPost.setVisibility(View.GONE);
        if(user.getUid().equals(eventUserId)){
            btDeletePost.setVisibility(View.VISIBLE);
        }else btDeletePost.setVisibility(View.GONE);

        tvEventName.setText(eventName);
        tvEventAddress.setText(eventAddress);
        tvEventDate.setText(eventDate);
        tvEventTime.setText(eventTime);
        tvEventDistance.setText(eventDistance);
        tvEventDetails.setText(eventDetails);
        tvEventPoster.setText("Posted By: " + eventUserName);

        final StorageReference imageRef = storage.getReferenceFromUrl(eventImgRef);
        Glide.with(this)
                .asBitmap()
                .load(imageRef)
                .into(imEvent);


        btEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUid().equals(eventUserId)) {
                    Intent editPost = new Intent(IndividualEventPage.this, EditEventActivity.class);
                    editPost.putExtra("eventName", eventName);
                    editPost.putExtra("eventAddress", eventAddress);
                    editPost.putExtra("eventDate", eventDate);
                    editPost.putExtra("eventTime", eventTime);
                    editPost.putExtra("eventDetails", eventDetails);
                    editPost.putExtra("imageRef", eventImgRef);
                    editPost.putExtra("eventId", eventId);
                    editPost.putExtra("eventUserId", eventUserId);
                    startActivity(editPost);
                }
            }
        });

        btDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getUid().equals(eventUserId)) {
                    new AlertDialog.Builder(IndividualEventPage.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to delete this event?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    db.collection("users").document(user.getUid()).collection("events").document(eventId).delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    if (!eventImgRef.equals("gs://boardapphht.appspot.com/images/party-hat.png")) {
                                                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(getApplicationContext(), "Event Successfully Deleted", Toast.LENGTH_LONG).show();
                                                                Intent myEvents = new Intent(IndividualEventPage.this, MyEventsActivity.class);
                                                                startActivity(myEvents);
                                                            }

                                                        });
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Event Successfully Deleted", Toast.LENGTH_LONG).show();
                                                        Intent myEvents = new Intent(IndividualEventPage.this, MyEventsActivity.class);
                                                        startActivity(myEvents);
                                                    }
                                                }
                                            });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

        Log.d("eventInfo", eventId + " " + eventName + " " + eventAddress + " " + eventDate + " " + eventTime+ " " +eventDistance+ " " +eventDetails+ " " +eventImgRef);
    }
}
