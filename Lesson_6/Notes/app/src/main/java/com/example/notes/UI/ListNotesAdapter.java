package com.example.notes.UI;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Data.DataNote;
import com.example.notes.Data.DataSource;
import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.Utils.DatePicker;

import java.util.Calendar;


public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {
    private DataSource dataSource;
    private OnItemClickListener itemClickListener;
    private Fragment fragment;

    public ListNotesAdapter(DataSource dataSource, Fragment fragment){
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotesAdapter.ViewHolder holder,
                                 int position) {
        holder.setData(dataSource.getDataNote(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView dateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dateTime = itemView.findViewById(R.id.date_time);

            itemView.findViewById(R.id.container_list_item).setOnClickListener(v -> {
                if (itemClickListener != null){
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void setData(DataNote dataNote){
            title.setText(dataNote.getName());
            initDatePicker(dataNote);
            dateTime.setText(dataNote.getDateTime());
        }

        private void initDatePicker(DataNote dataNote) {
            if(dataNote.getDateTime() == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dataNote.setDateTime(android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString());
            }

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
                datePickerDialogFragment.show(fragment.getActivity().getSupportFragmentManager(), "");
            });
        }
    }
}

