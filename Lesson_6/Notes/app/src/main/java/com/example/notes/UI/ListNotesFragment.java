package com.example.notes.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.Data.DataNote;
import com.example.notes.Data.DataSource;
import com.example.notes.Data.DataSourceImplement;
import com.example.notes.NoteFragment;
import com.example.notes.R;
import com.example.notes.Utils.Constants;
import com.example.notes.Utils.DatePicker;

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
        DataSource data = new DataSourceImplement(getResources()).init();
        initRecyclerView(view, data);
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

    private void initRecyclerView(View view, DataSource data) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        ListNotesAdapter adapter = new ListNotesAdapter(data, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener((itemView, position) -> {
            showNote(data.getDataNote(position));
        });
    }

    private int convertDpToPixel(double dp){
        return (int) dp * (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void showNote(DataNote data) {
        NoteFragment noteWindow = NoteFragment.newInstance(data);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.list_notes, noteWindow).
                addToBackStack(null).
                commit();
    }
}

























