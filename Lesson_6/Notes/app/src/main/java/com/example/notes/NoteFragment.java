package com.example.notes;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class NoteFragment extends Fragment {

    private static final String NOTE_INFO = "Note Info";

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NOTE_INFO, dataNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initList((LinearLayout) view);
        return view;
    }

    private void initList(LinearLayout view) {
        TextView name = view.findViewById(R.id.text_note_name);
        TextView description = view.findViewById(R.id.text_note_body);
        description.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

        name.setText(dataNote.getName());
        description.setText(dataNote.getDescription());
    }

    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fm.popBackStack();
        }
    }
}
