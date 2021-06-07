package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable{
   private StringBuilder screenText = new StringBuilder();
   private StringBuilder historyText = new StringBuilder();
    private String operationType = "%%";

    private boolean isOperationFirst = true;

    private Double operand = 0.0;
    private Double result = 0.0;

    public Data() {
    }

    protected Data(Parcel in) {
        operationType = in.readString();
        isOperationFirst = in.readByte() != 0;
        if (in.readByte() == 0) {
            operand = null;
        } else {
            operand = in.readDouble();
        }
        if (in.readByte() == 0) {
            result = null;
        } else {
            result = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(operationType);
        dest.writeByte((byte) (isOperationFirst ? 1 : 0));
        if (operand == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(operand);
        }
        if (result == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(result);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public Double getOperand() {
        return operand;
    }

    public void setOperand(Double operand) {
        this.operand = operand;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public StringBuilder getScreenText() {
        return screenText;
    }

    public void setScreenText(String text) {
        this.screenText = screenText.append(text);
    }

    public StringBuilder getHistoryText() {
        return historyText;
    }

    public void setHistoryText(String text) {
        this.historyText = historyText.append(text);
    }

    public void deleteHistoryText() {
        this.historyText = historyText.delete(0, historyText.length());
    }

    public void deleteScreenText() {
        this.screenText = screenText.delete(0, screenText.length());
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public boolean isOperationFirst() {
        return isOperationFirst;
    }

    public void setOperationFirst(boolean operationFirst) {
        isOperationFirst = operationFirst;
    }
}
