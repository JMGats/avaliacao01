package com.example.avaliacao01;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MonitorExerciseActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textViewSpeed;
    private Chronometer chronometer;
    private TextView textViewDistance;
    private TextView textViewCalories;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_exercise);

        textViewSpeed = findViewById(R.id.textViewSpeed);
        chronometer = findViewById(R.id.chronometer);
        textViewDistance = findViewById(R.id.textViewDistance);
        textViewCalories = findViewById(R.id.textViewCalories);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }
}
