package com.example.avaliacao01;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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
        atualizaLastLocationView(null);
        atualizaLocationUpdatesView(null);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }


    private void atualizaLocationUpdatesView(Location location) {
        TextView tv_lu = (TextView) findViewById(R.id.tv_currentLocation);
        String mens = "Dados da posição atual\n";
        if (location != null) {
            mens += String.valueOf("Latitude(graus)=" + location.getLatitude()) + "\n"
                    + String.valueOf("Longitude(graus)=" + location.getLongitude()) + "\n"
                    + String.valueOf("Velocidade(m/s)=" + location.getSpeed()) + "\n"
                    + String.valueOf("Rumo(graus)=" + location.getBearing() + "\n"
                    + String.valueOf("Acurácia(metros)=" + location.getAccuracy()));
        } else {
            mens += "Não coletando informações de atualização";
        }
        tv_lu.setText(mens);
    }

    private void atualizaLastLocationView(Location location) {
        TextView tv_ll = (TextView) findViewById(R.id.tv_lastlocation);
        String mens = "Dados da Última posição\n";
        if (location != null) {
            mens += String.valueOf("Latitude(graus)=" + location.getLatitude()) + "\n"
                    + String.valueOf("Longitude(graus)=" + location.getLongitude()) + "\n"
                    + String.valueOf("Velocidade(m/s)=" + location.getSpeed()) + "\n"
                    + String.valueOf("Rumo(graus)=" + location.getBearing());
        } else {
            mens += "Não disponível";
        }
        tv_ll.setText(mens);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permissões de localização
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LAST_LOCATION);
            return;
        }

        private void startLocationUpdates() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Solicitar permissões de localização
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_UPDATES);
                return;
            } else {
                // Iniciar as atualizações de localização
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            }
        }


        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // Processar a última localização conhecida
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Localização Atual"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                }
            }
        });
    }

    private void stopLocationUpdates() {
        if (mFusedLocationProviderClient != null) {
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }
}
