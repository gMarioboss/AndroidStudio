package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String DATA_TEXT = "Data Texts";

    private Data data = new Data();
    private Operations operation = new Operations(data);

    private TextView screen;
    private TextView history;

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(DATA_TEXT, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        data = instanceState.getParcelable(DATA_TEXT);
        setRestoredTexts();
    }

    private void setRestoredTexts() {
        setHistory(data.getHistoryText().toString());
        setScreen(data.getResult().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initButtons();
    }

    private void initViews() {
        screen = findViewById(R.id.actual_operation);
        history = findViewById(R.id.history);
    }

    private void initButtons() {
        findViewById(R.id.button_0).setOnClickListener(v -> {
            addToOperand("0");
            makeHistory("0");
        });
        findViewById(R.id.button_1).setOnClickListener(v -> {
            addToOperand("1");
            makeHistory("1");
        });
        findViewById(R.id.button_2).setOnClickListener(v -> {
            addToOperand("2");
            makeHistory("2");
        });
        findViewById(R.id.button_3).setOnClickListener(v -> {
            addToOperand("3");
            makeHistory("3");
        });
        findViewById(R.id.button_4).setOnClickListener(v -> {
            addToOperand("4");
            makeHistory("4");
        });
        findViewById(R.id.button_5).setOnClickListener(v -> {
            addToOperand("5");
            makeHistory("5");
        });
        findViewById(R.id.button_6).setOnClickListener(v -> {
            addToOperand("6");
            makeHistory("6");
        });
        findViewById(R.id.button_7).setOnClickListener(v -> {
            addToOperand("7");
            makeHistory("7");
        });
        findViewById(R.id.button_8).setOnClickListener(v -> {
            addToOperand("8");
            makeHistory("8");
        });
        findViewById(R.id.button_9).setOnClickListener(v -> {
            addToOperand("9");
            makeHistory("9");
        });
        findViewById(R.id.button_point).setOnClickListener(v -> {
            addToOperand(".");
            makeHistory(".");
        });
        findViewById(R.id.button_plus).setOnClickListener(v -> {
            result();
            data.setOperationType('+');
            makeHistory("+");
        });
        findViewById(R.id.button_minus).setOnClickListener(v -> {
            result();
            data.setOperationType('-');
            makeHistory("-");
        });
        findViewById(R.id.button_division).setOnClickListener(v -> {
            result();
            data.setOperationType('/');
            makeHistory("/");
        });
        findViewById(R.id.button_X).setOnClickListener(v -> {
            result();
            data.setOperationType('*');
            makeHistory("*");
        });
        findViewById(R.id.button_plus_minus).setOnClickListener(v -> {
            result();
            data.setOperationType('p');
        });
        findViewById(R.id.button_percent).setOnClickListener(v -> {
            result();
            data.setOperationType('%');
            makeHistory("%");
        });
        findViewById(R.id.button_equally).setOnClickListener(v -> {
            result();
            data.setOperationType('=');
            makeHistory("=");
        });
        findViewById(R.id.button_c).setOnClickListener(v -> {
            clearEverything();
        });
    }

    private void result() {
        try {
            if (data.isOperationFirst()) {
                data.setResult(Double.parseDouble(data.getScreenText().toString()));
            } else {
                data.setOperand(Double.parseDouble(data.getScreenText().toString()));
            }
        } catch (NumberFormatException e) {
            setScreen(data.getScreenText().toString());
        }
        switch (data.getOperationType()) {
            case '+':
                operation.sum();
                setScreen(data.getResult().toString());
                break;
            case '-':
                operation.subtraction();
                setScreen(data.getResult().toString());
                break;
            case '*':
                operation.multiplication();
                setScreen(data.getResult().toString());
                break;
            case '/':
                operation.division();
                setScreen(data.getResult().toString());
                break;
            case 'p':
            case '%':
                clearEverything();
                break;
            case '=':
                setScreen(data.getResult().toString());
                data.deleteHistoryText();
                makeHistory(data.getResult().toString());
        }
        data.setOperationFirst(false);
        data.deleteScreenText();
        data.setOperand(0.0);
    }

    private void addToOperand(String digit) {
        data.setScreenText(digit);
        setScreen(data.getScreenText().toString());
    }

    private void makeHistory(String str) {
        data.setHistoryText(str);
        setHistory(data.getHistoryText().toString());
    }

    private void setHistory(String text) {
        history.setText(String.format(Locale.ENGLISH, text));
    }

    private void setScreen(String text) {
        screen.setText(String.format(Locale.ENGLISH, text));
    }

    private void clearEverything() {
        data.setOperationFirst(true);
        setScreen("0");
        setHistory("0");
        data.deleteHistoryText();
        data.deleteScreenText();
        data.setOperationType('0');
        data.setResult(0.0);
    }
}