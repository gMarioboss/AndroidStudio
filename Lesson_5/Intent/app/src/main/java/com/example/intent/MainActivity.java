package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton runButton = findViewById(R.id.test_calculator);
        runButton.setOnClickListener(v -> {
            Uri uri = Uri.parse("test.calculator://intent");
            Intent runCalculatorIntent = new Intent(Intent.ACTION_VIEW, uri);
            ActivityInfo activityInfo = runCalculatorIntent.resolveActivityInfo(getPackageManager(),
                    runCalculatorIntent.getFlags());
            if (activityInfo != null) {
                startActivity(runCalculatorIntent);
            }
        });
    }
}