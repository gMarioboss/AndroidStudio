package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class DataNote implements Parcelable {

    protected DataNote(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateTime = in.readString();
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

    public DataNote (String name, String description) {
        this.name = name;
        this.description = description;
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
    private String name;

    private String description;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String dateTime;

}
