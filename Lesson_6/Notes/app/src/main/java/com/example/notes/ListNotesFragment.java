package com.example.notes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ListNotesFragment extends Fragment implements Constants {

    private DataNote dataNote;

    public ListNotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            dataNote = savedInstanceState.getParcelable(NOTE_INFO);
        } else {
            dataNote = new DataNote(getResources().getStringArray(R.array.notes)[0],
                    getResources().getStringArray(R.array.notes_description)[0]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        initList(view.findViewById(R.id.container_notes));
        initPopupMenu(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_INFO, dataNote);
        super.onSaveInstanceState(outState);
    }

    private void initPopupMenu(View view) {
        AppCompatImageButton button = view.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.popup_add:
                                Toast.makeText(getContext(), "New note added", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void initList(LinearLayout view) {
        String[] notes = getResources().getStringArray(R.array.notes);

        for (int i = 0; i < notes.length; i++) {
            String name = notes[i];
            String description = getResources().getStringArray(R.array.notes_description)[i];

            LinearLayout linearLayout = customizeLayout();

            linearLayout.addView(customizeName(name));
            linearLayout.addView(customizeDate());
            view.addView(linearLayout);

            linearLayout.setOnClickListener(v -> {
                dataNote = new DataNote(name, description);
                showNote();
            });
        }
    }

    @NotNull
    private TextView customizeDate() {
        TextView dateTime = new TextView(requireContext());
        dateTime.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.8f
        ));
        dateTime.setGravity(Gravity.BOTTOM);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dataNote.setDateTime(android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString());

        dateTime.setOnClickListener(v -> {
                        DatePicker datePickerDialogFragment = new DatePicker();
                        datePickerDialogFragment.setListener(new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                dataNote.setDateTime(android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString());
                                dateTime.setText(dataNote.getDateTime());
                            }
                        });
            datePickerDialogFragment.show(requireActivity().getSupportFragmentManager(), "");
        });

        dateTime.setText(dataNote.getDateTime());
        return dateTime;
    }

    @NotNull
    private TextView customizeName(String name) {
        TextView nameNote = new TextView(requireContext());
        nameNote.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.5f
        ));
        nameNote.setText(name);
        return nameNote;
    }

    private LinearLayout customizeLayout() {
        LinearLayout linearLayout = new LinearLayout(requireContext());
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                convertDpToPixel(100));

        params.setMargins(convertDpToPixel(20), convertDpToPixel(12),
                convertDpToPixel(20), convertDpToPixel(12));
        linearLayout.setPadding(convertDpToPixel(30), convertDpToPixel(15), 0, convertDpToPixel(15));
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        createBordersIfLandScape(linearLayout);
        return linearLayout;
    }

    private void createBordersIfLandScape(LinearLayout linearLayout) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(NameSharedPreference, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_DARK_MODE, false)) {
            GradientDrawable gradientDrawable=new GradientDrawable();
            gradientDrawable.setStroke(4,requireActivity().getColor(R.color.white));
            linearLayout.setBackground(gradientDrawable);
        } else {
            linearLayout.setBackgroundColor(requireActivity().getColor(R.color.white));
        }
    }

    private int convertDpToPixel(double dp){
        return (int) dp * (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void showNote() {
        NoteFragment noteWindow = NoteFragment.newInstance(dataNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.list_notes, noteWindow).
                addToBackStack(null).
                commit();
    }
}

























