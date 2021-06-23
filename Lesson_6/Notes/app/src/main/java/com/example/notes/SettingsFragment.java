package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.notes.Utils.Constants;

public class SettingsFragment extends Fragment implements Constants {

    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initSettings(view);
        return view;
    }

    private void initSettings(View view) {
        SwitchCompat switchButton = view.findViewById(R.id.night_mode_switch);
        sharedPreferences = requireActivity().getSharedPreferences(NameSharedPreference, Context.MODE_PRIVATE);
        switchButton.setChecked(sharedPreferences.getBoolean(KEY_DARK_MODE, false));
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveNightMode(isChecked);
                requireActivity().recreate();
            }
        });
    }

    private void saveNightMode(boolean darkMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_DARK_MODE, darkMode).apply();
    }
}