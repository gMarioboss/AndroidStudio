package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Operations implements Parcelable {
    private Data data;

    public Operations(Data data) {
        this.data = data;
    }


    protected Operations(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Operations> CREATOR = new Creator<Operations>() {
        @Override
        public Operations createFromParcel(Parcel in) {
            return new Operations(in);
        }

        @Override
        public Operations[] newArray(int size) {
            return new Operations[size];
        }
    };

    void percent () {
        data.setResult(data.getOperand()*20/100);
    }

    void sum() {
        data.setResult(data.getResult() + data.getOperand());
    }

    void subtraction() {
        data.setResult(data.getResult() - data.getOperand());
    }

    void multiplication() {
        data.setResult(data.getResult() * data.getOperand());
    }

    void division() {
        data.setResult(data.getResult() / data.getOperand());
    }
}




