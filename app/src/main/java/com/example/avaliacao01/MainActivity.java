package com.example.avaliacao01;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            obterLocalizacao();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    obterLocalizacao();
                } else {
                    Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show();
                    Log.e("LocationPermission", "Permissão de localização negada");
                }
                return;
            }
        }
    }

    private void obterLocalizacao() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Log.d("LocationUpdates", "Solicitação de atualizações de localização feita com sucesso");
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("LocationError", "Erro ao solicitar atualizações de localização: " + e.getMessage());
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();

            locationManager.removeUpdates(this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("LocationStatus", "Status da localização alterado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("LocationProvider", "Provedor de localização habilitado: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("LocationProvider", "Provedor de localização desabilitado: " + provider);
        }
    };

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
        Intent intent = new Intent(this, MonitorExerciseActivity.class);
        startActivity(intent);
    }
}
