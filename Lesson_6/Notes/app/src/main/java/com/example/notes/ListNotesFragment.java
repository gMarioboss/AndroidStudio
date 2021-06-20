package com.example.notes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ListNotesFragment extends Fragment {

    private static final String NOTE_INFO = "Note Info";

    private DataNote dataNote;
    private boolean isLandscapeMode;

    public ListNotesFragment() {
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        isLandscapeMode = getResources().getConfiguration().
                orientation == Configuration.ORIENTATION_LANDSCAPE;
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

        if (isLandscapeMode) {
            showNote();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        initList((LinearLayout) view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_INFO, dataNote);
        super.onSaveInstanceState(outState);
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
        linearLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    private int convertDpToPixel(double dp){
        return (int) dp * (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void showNote() {
        NoteFragment noteWindow = NoteFragment.newInstance(dataNote);
        if (isLandscapeMode) {
            showLandscapeMode(noteWindow);
        } else {
            showPortraitMode(noteWindow);
        }
    }

    private void showPortraitMode(NoteFragment noteWindow) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.list_notes, noteWindow).
                addToBackStack(null).
                commit();
    }

    private void showLandscapeMode(NoteFragment noteWindow) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.container_note_fragment, noteWindow).
                commit();
    }
}

























