package com.example.notes.Data;

import android.content.res.Resources;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class DataSourceImplement implements DataSource {

    private List<DataNote> dataList;
    private Resources resources;

    public DataSourceImplement(Resources resources) {
        this.dataList = new ArrayList<>(3);
        this.resources = resources;
    }

    public DataSourceImplement init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.notes_description);
        for (int i = 0; i < titles.length; i++) {
            dataList.add(new DataNote(titles[i], descriptions[i]));
        }
        return this;
    }

    @Override
    public DataNote getDataNote(int position) {
        return dataList.get(position);
    }

    @Override
    public int getSize() {
        return dataList.size();
    }
}
