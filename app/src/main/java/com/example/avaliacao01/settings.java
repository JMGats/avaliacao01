package com.example.avaliacao01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    private RadioGroup radioGroupExerciseType;
    private RadioGroup radioGroupSpeedUnit;
    private RadioGroup radioGroupMapOrientation;
    private RadioGroup radioGroupMapType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroupExerciseType = findViewById(R.id.GroupExerciseType);
        radioGroupSpeedUnit = findViewById(R.id.GroupSpeedUnit);
        radioGroupMapOrientation = findViewById(R.id.GroupMapOrientation);
        radioGroupMapType = findViewById(R.id.GroupMapType);

        Button buttonSaveSettings = findViewById(R.id.buttonSaveSettings);
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        loadSettings(); // Carregar as configurações salvas ao iniciar a tela de configurações.
    }

    private void saveSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Salvar o ID do RadioButton selecionado para cada grupo de opções
        editor.putInt("ExerciseType", radioGroupExerciseType.getCheckedRadioButtonId());
        editor.putInt("SpeedUnit", radioGroupSpeedUnit.getCheckedRadioButtonId());
        editor.putInt("MapOrientation", radioGroupMapOrientation.getCheckedRadioButtonId());
        editor.putInt("MapType", radioGroupMapType.getCheckedRadioButtonId());

        editor.apply();

        Toast.makeText(this, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int exerciseType = sharedPreferences.getInt("ExerciseType", -1);
        int speedUnit = sharedPreferences.getInt("SpeedUnit", -1);
        int mapOrientation = sharedPreferences.getInt("MapOrientation", -1);
        int mapType = sharedPreferences.getInt("MapType", -1);

        // Verificar e restaurar a seleção dos RadioButtons com base nos IDs salvos
        if (exerciseType != -1) {
            RadioButton selectedRadioButton = findViewById(exerciseType);
            selectedRadioButton.setChecked(true);
        }

        if (speedUnit != -1) {
            RadioButton selectedRadioButton = findViewById(speedUnit);
            selectedRadioButton.setChecked(true);
        }

        if (mapOrientation != -1) {
            RadioButton selectedRadioButton = findViewById(mapOrientation);
            selectedRadioButton.setChecked(true);
        }

        if (mapType != -1) {
            RadioButton selectedRadioButton = findViewById(mapType);
            selectedRadioButton.setChecked(true);
        }
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}