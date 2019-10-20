package com.example.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListEventsActivity extends AppCompatActivity {
    private TextView tvLat;
    private TextView tvLng;
    private TextView tvDistance;
    private SeekBar sbDistance;
    private Button btnUpdateDist;

    private double distance;
    //3.6 = 5 mile radius, distance * 0.72
    private GeoLocation myLocation;
    private GeoLocation[] boundingCoords;
    private final double radius = 3958.8;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    final private int LOCATION_PERMISSION_REQUEST_CODE = 103;

    private static final String TAG = "ListEvents";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList <String> mImagesURL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        tvLat = findViewById(R.id.tvLat);
        tvLng = findViewById(R.id.tvLng);
        tvDistance = findViewById(R.id.tvDistance);
        sbDistance = findViewById(R.id.sbDistance);
        btnUpdateDist = findViewById(R.id.btnUpdateDist);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationPermission();
        distance = 7.2;

        initImageBitmaps();
        Spinner spinner = findViewById(R.id.spinner);


        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDistance.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnUpdateDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = Double.parseDouble(tvDistance.getText().toString()) * 0.72;
                getBoundingCoord();
            }
        });
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


        mImagesURL.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

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



    private void getBoundingCoord(){
        boundingCoords = myLocation.boundingCoordinates(distance,radius);
        String bCoord1 = Double.toString(boundingCoords[0].getLatitudeInDegrees());
        String bCoord2 = Double.toString(boundingCoords[0].getLongitudeInDegrees());
        String bCoord3 = Double.toString(boundingCoords[1].getLatitudeInDegrees());
        String bCoord4 = Double.toString(boundingCoords[1].getLongitudeInDegrees());

        Log.i("Bounding Cordinates",bCoord1 + " " + bCoord2 + " " + bCoord3 + " " + bCoord4);

    }

    private void getLocationPermission(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
        else getDeviceLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getDeviceLocation();
                }
            }
        }
    }

    private void getDeviceLocation(){
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null){
                                tvLat.setText(Double.toString(mLastKnownLocation.getLatitude()));
                                tvLng.setText(Double.toString(mLastKnownLocation.getLongitude()));
                                myLocation = GeoLocation.fromDegrees(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                                getBoundingCoord();
                            }
                            else {
                                Log.d("locationErr", "Cannot get location");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("locationErr", e.toString());
            }
        });
    }
}
