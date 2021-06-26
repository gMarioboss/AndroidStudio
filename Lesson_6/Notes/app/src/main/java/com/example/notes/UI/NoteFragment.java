package com.example.notes.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notes.Data.DataNote;
import com.example.notes.MainActivity;
import com.example.notes.Observer.Publisher;
import com.example.notes.R;
import com.example.notes.Utils.Constants;

public class NoteFragment extends Fragment implements Constants {

    private DataNote dataNote;
    private Publisher publisher;
    private EditText title;
    private EditText description;

    public static NoteFragment newInstance(DataNote dataNote) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(NOTE_INFO, dataNote);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initList((LinearLayout) view);

        if(dataNote != null) {
            populateView();
        }

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        dataNote = collectDataNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingleNote(dataNote);
    }

    private DataNote collectDataNote() {
        String name = this.title == null ? "" : this.title.getText().toString();

        String description = this.title == null ? "" : this.description.getText().toString();

        return new DataNote(name, description);
    }

    private void initList(LinearLayout view) {
        title = view.findViewById(R.id.text_note_name);
        description = view.findViewById(R.id.text_note_body);
        createBordersIfNightMode(title);
        createBordersIfNightMode(description);
    }

    private void populateView() {
        title.setText(dataNote.getName());
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
