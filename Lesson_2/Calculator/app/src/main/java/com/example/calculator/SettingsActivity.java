package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonLightMode = findViewById(R.id.light_mode);
        Button buttonDarkMode = findViewById(R.id.dark_mode);

        buttonLightMode.setOnClickListener(v -> {
            Intent intentLightMode = new Intent();
            intentLightMode.putExtra(CHOOSE_THEME, false);
            setResult(RESULT_OK, intentLightMode);
            finish();
        });

        buttonDarkMode.setOnClickListener(v -> {
            Intent intentDarkMode = new Intent();
            intentDarkMode.putExtra(CHOOSE_THEME, true);
            setResult(RESULT_OK, intentDarkMode);
            finish();
        });
    }
}