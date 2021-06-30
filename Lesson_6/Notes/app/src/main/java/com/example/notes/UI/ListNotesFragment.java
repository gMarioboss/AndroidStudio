package com.example.notes.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notes.Data.DataNote;
import com.example.notes.Data.DataSource;
import com.example.notes.Data.DataSourceImplement;
import com.example.notes.MainActivity;
import com.example.notes.Navigation;
import com.example.notes.Observer.Observer;
import com.example.notes.Observer.Publisher;
import com.example.notes.R;
import com.example.notes.Utils.Constants;

import org.jetbrains.annotations.NotNull;

public class ListNotesFragment extends Fragment implements Constants {

    private DataSource data;
    private ListNotesAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new DataSourceImplement(getResources()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);
        initRecyclerView(view, data);
        initPopupMenu(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
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
                        switch (item.getItemId()) {
                            case R.id.popup_add:
                                navigation.addFragment(NoteFragment.newInstance(), true);
                                publisher.subscribe(new Observer() {
                                    @Override
                                    public void updateCardData(DataNote dataNote) {
                                        data.addCardData(dataNote);
                                        adapter.notifyItemInserted(data.getSize() - 1);
                                        recyclerView.scrollToPosition(data.getSize() - 1);
                                    }
                                });
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()) {
            case R.id.action_change:
                navigation.addFragment(NoteFragment.newInstance(data.getDataNote(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(DataNote dataNote) {
                        data.updateCardData(position, dataNote);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initRecyclerView(View view, DataSource data) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        setItemSeparator(recyclerView);
        setItemAnimation(data, recyclerView);

        adapter = new ListNotesAdapter(data, this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((itemView, position) -> {
            navigation.addFragment(NoteFragment.newInstance(data.getDataNote(position)), true);
            publisher.subscribe(new Observer() {
                @Override
                public void updateCardData(DataNote dataNote) {
                    data.updateCardData(position, dataNote);
                    adapter.notifyItemChanged(position);
                }
            });
        });
    }

    private void setItemAnimation(DataSource data, RecyclerView recyclerView) {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(DEFAULT_DURATION);
        animator.setRemoveDuration(DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
    }

    private void setItemSeparator(RecyclerView recyclerView) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

}

























