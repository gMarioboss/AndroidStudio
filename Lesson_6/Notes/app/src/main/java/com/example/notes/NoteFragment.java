package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notes.Data.DataNote;
import com.example.notes.Utils.Constants;

public class NoteFragment extends Fragment implements Constants {

    private DataNote dataNote;

    public NoteFragment() {
    }

    public static NoteFragment newInstance(DataNote dataNote) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_INFO, dataNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataNote = getArguments().getParcelable(NOTE_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initList((LinearLayout) view);
        return view;
    }

    private void initList(LinearLayout view) {
        EditText name = view.findViewById(R.id.text_note_name);
        EditText description = view.findViewById(R.id.text_note_body);
        createBordersIfNightMode(name);
        createBordersIfNightMode(description);
        name.setText(dataNote.getName());
        description.setText(dataNote.getDescription());
    }

    private void createBordersIfNightMode(TextView textView) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(NameSharedPreference, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_DARK_MODE, false)) {
            GradientDrawable gradientDrawable=new GradientDrawable();
            gradientDrawable.setStroke(4,requireActivity().getColor(R.color.white));
            textView.setBackground(gradientDrawable);
        } else {
            textView.setBackgroundColor(requireActivity().getColor(R.color.white));
        }
    }

}
