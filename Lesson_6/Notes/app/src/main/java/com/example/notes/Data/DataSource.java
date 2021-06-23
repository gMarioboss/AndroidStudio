package com.example.notes.Data;

public interface DataSource {
    DataNote getDataNote(int position);
    int getSize();
}
