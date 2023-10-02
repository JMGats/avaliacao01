package com.example.avaliacao01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class user_profile extends AppCompatActivity {

    private EditText editTextWeight, editTextHeight, editTextBirthDate;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        loadProfile();
    }

    private void saveProfile() {
        String weight = editTextWeight.getText().toString();
        String height = editTextHeight.getText().toString();
        String birthDate = editTextBirthDate.getText().toString();
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        String gender = "";

        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Gender", gender);
        editor.putString("Weight", weight);
        editor.putString("Height", height);
        editor.putString("BirthDate", birthDate);
        if (selectedGenderId == radioButtonMale.getId()) {
            editor.putString("Gender", "Masculino");
        } else if (selectedGenderId == radioButtonFemale.getId()) {
            editor.putString("Gender", "Feminino");
        }

        editor.apply();

        Toast.makeText(this, "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void loadProfile() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String gender = sharedPreferences.getString("Gender", "");
        String weight = sharedPreferences.getString("Weight", "");
        String height = sharedPreferences.getString("Height", "");
        String birthDate = sharedPreferences.getString("BirthDate", "");

        RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
        RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);

        if (gender.equals("Masculino")) {
            radioButtonMale.setChecked(true);
        } else if (gender.equals("Feminino")) {
            radioButtonFemale.setChecked(true);
        }
        editTextWeight.setText(weight);
        editTextHeight.setText(height);
        editTextBirthDate.setText(birthDate);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}