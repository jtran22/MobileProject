package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    private Button btGoEvents;
    private Button btFindEvents;
    private Button btPostEvents;

    private Intent myEvents;
    private Intent listEvents;
    private Intent postEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btGoEvents = findViewById(R.id.bt_go_events);
        btFindEvents = findViewById(R.id.bt_find_events);
        btPostEvents = findViewById(R.id.bt_post_events);

        btGoEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEvents = new Intent(HomeActivity.this, MyEventsActivity.class);
                startActivity(myEvents);
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
                postEvents = new Intent(HomeActivity.this, EditActivity.class);
                startActivity(postEvents);
            }
        });
    }
}
