package com.example.notes.Data;

public interface DataSource {
    DataNote getDataNote(int position);
    int getSize();
    void deleteCardData(int position);
    void updateCardData(int position, DataNote dataNote);
    void addCardData(DataNote dataNote);
}
