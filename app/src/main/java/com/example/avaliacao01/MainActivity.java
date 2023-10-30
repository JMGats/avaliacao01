package com.example.avaliacao01;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToUser(View view) {
        Intent intent = new Intent(this, user_profile.class);
        startActivity(intent);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }

    public void goToAbout(View view) {
        Intent intent = new Intent(this, about.class);
        startActivity(intent);
    }
    public void goMonitorExercise(View view) {
        Intent intent = new Intent(this, monitor_exercise.class);
        startActivity(intent);
    }


}