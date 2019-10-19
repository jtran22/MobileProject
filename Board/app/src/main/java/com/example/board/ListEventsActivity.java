package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ListEventsActivity extends AppCompatActivity {

    private static final String TAG = "ListEvents";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList <String> mImagesURL = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        Log.d(TAG,"OnCreate: Started");
        initImageBitmaps();
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.distances,android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerAdapter);



    }
    private void initImageBitmaps()
    {
        Log.d(TAG,"initImagesBitmaps: preparing Bitaps");
        mImagesURL.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImagesURL.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImagesURL.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImagesURL.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImagesURL.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImagesURL.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


//        mImagesURL.add("https://i.redd.it/glin0nwndo501.jpg");
//        mNames.add("White Sands Desert");
//
//        mImagesURL.add("https://i.redd.it/obx4zydshg601.jpg");
//        mNames.add("Austrailia");
//
//        mImagesURL.add("https://i.imgur.com/ZcLLrkY.jpg");
//        mNames.add("Washington");

        initRecyclerView();
    }

    private void initRecyclerView()
    {
        Log.d(TAG,"initRecyclerView: inti recycler");
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mNames,mImagesURL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
