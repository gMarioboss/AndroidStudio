package com.example.notes.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class DataNote implements Parcelable {

    private String id;
    private String description;
    private String dateTime;
    private String name;

    protected DataNote(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateTime = in.readString();
    }

    public DataNote (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public DataNote (String name, String description, String dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataNote> CREATOR = new Creator<DataNote>() {
        @Override
        public DataNote createFromParcel(Parcel in) {
            return new DataNote(in);
        }

        @Override
        public DataNote[] newArray(int size) {
            return new DataNote[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
