package com.example.calculator;

public class Operations {
    private Data data;

    public Operations(Data data) {
        this.data = data;
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




