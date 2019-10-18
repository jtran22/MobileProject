package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private ImageButton btGoEvents;
    private ImageButton btFindEvents;
    private ImageButton btPostEvents;
    private ImageButton btProfile;

    private TextView tvHomeScreen;

    private Intent myEvents;
    private Intent listEvents;
    private Intent postEvents;
    private Intent profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();

        btGoEvents = findViewById(R.id.bt_go_events);
        btFindEvents = findViewById(R.id.bt_find_events);
        btPostEvents = findViewById(R.id.bt_post_events);
        btProfile = findViewById(R.id.btProfile);

        tvHomeScreen = findViewById(R.id.tvHomeScreen);


        String displayName = intent.getStringExtra("displayName");

        tvHomeScreen.setText("Hello " + displayName + "!");

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btGoEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEvents = new Intent(HomeActivity.this, MyEventsActivity.class);
                startActivity(myEvents);
            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             profile = new Intent(HomeActivity.this,ProfileActivity.class);
             startActivity(profile);
            }
        });

        btFindEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listEvents = new Intent(HomeActivity.this, ListEventsActivity.class);
                startActivity(listEvents);
            }
        });

        btPostEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEvents = new Intent(HomeActivity.this, PostEventActivity.class);
                startActivity(postEvents);
            }
        });
    }
}
