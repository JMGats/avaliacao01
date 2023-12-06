package com.example.avaliacao01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class monitor_exercise extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_LAST_LOCATION = 1;
    private static final int REQUEST_LOCATION_UPDATES = 2;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    public void goToMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_exercise);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Button start = (Button) findViewById(R.id.bt_start);
        start.setOnClickListener(this);
        Button stop = (Button) findViewById(R.id.bt_stop);
        stop.setOnClickListener(this);
        Button get = (Button) findViewById(R.id.bt_get);
        get.setOnClickListener(this);
// obtém a última localização
        //não descobri oq passar aqui.
        atualizaLastLocationView(null);
        atualizaLocationUpdatesView(null);
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

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_get)
            lastLocation();
        if (view.getId() == R.id.bt_start)
            startLocationUpdates();
        if (view.getId() == R.id.bt_stop)
            stopLocationUpdates();
    }


    private void lastLocation() {
// Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
// A permissão foi dada – OK vá em frente
            mFusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            atualizaLastLocationView(location); // processa a localização
                        }
                    });
        } else {
// Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LAST_LOCATION);
        }
    }


    private void startLocationUpdates() {
// Se a app já possui a permissão, ativa a calamada de localização
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
// A permissão foi dada– OK vá em frente
// 3. Cria o cliente (FusedLocationProviderClient)
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
// 4. Configura a solicitação de localizações (LocationRequest)
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5 * 1000);
            mLocationRequest.setFastestInterval(1 * 1000);
// 5. Programa o escutador para consumir as novas localizações geradas (LocationCallback)
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Location location = locationResult.getLastLocation();
                    atualizaLocationUpdatesView(location); // processa a localização
                }
            };
// 6. Manda o cliente começar a gerar atualizações de localização.
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, null);
        } else {
// Solicite a permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_UPDATES);
        }
    }


    private void stopLocationUpdates() {
        if (mFusedLocationProviderClient != null)

            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        atualizaLocationUpdatesView(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //CONTINUAÇÂO
        if (requestCode == REQUEST_LOCATION_UPDATES) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
// O usuário acabou de dar a permissão
                startLocationUpdates();
            } else {
// O usuário não deu a permissão solicitada
                Toast.makeText(this, "Sem permissão para mostrar atualizações da sua localização",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}