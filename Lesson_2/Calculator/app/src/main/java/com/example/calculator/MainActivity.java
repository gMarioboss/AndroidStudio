package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String DATA_TEXT = "Data Texts";
    private static final String OPERATIONS = "Operations";
    private static final Double ZERO = 0.0;

    private Data data = new Data();
    private Operations operation = new Operations(data);

    private TextView screen;
    private TextView history;

    @Override
    protected void onSaveInstanceState(Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(DATA_TEXT, data);
        instanceState.putParcelable(OPERATIONS, operation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        data = instanceState.getParcelable(DATA_TEXT);
        operation = instanceState.getParcelable(OPERATIONS);
        setRestoredTexts();
    }

    private void setRestoredTexts() {
        setTextOnHistory(data.getHistoryText().toString());
        setTextOnScreen(data.getResult().toString());
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
            sendAndShowData(Values.NUMBER_0.getValue());
        });
        findViewById(R.id.button_1).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_1.getValue());
        });
        findViewById(R.id.button_2).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_2.getValue());
        });
        findViewById(R.id.button_3).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_3.getValue());
        });
        findViewById(R.id.button_4).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_4.getValue());
        });
        findViewById(R.id.button_5).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_5.getValue());
        });
        findViewById(R.id.button_6).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_6.getValue());
        });
        findViewById(R.id.button_7).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_7.getValue());
        });
        findViewById(R.id.button_8).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_8.getValue());
        });
        findViewById(R.id.button_9).setOnClickListener(v -> {
            sendAndShowData(Values.NUMBER_9.getValue());
        });
        findViewById(R.id.button_point).setOnClickListener(v -> {
            sendAndShowData(Values.POINT.getValue());
        });
        findViewById(R.id.button_plus).setOnClickListener(v -> {
            setOperation(Values.OPER_PLUS.getValue());
        });
        findViewById(R.id.button_minus).setOnClickListener(v -> {
            setOperation(Values.OPER_MINUS.getValue());
        });
        findViewById(R.id.button_division).setOnClickListener(v -> {
            setOperation(Values.OPER_DIV.getValue());
        });
        findViewById(R.id.button_X).setOnClickListener(v -> {
            setOperation(Values.OPER_MULT.getValue());
        });
        findViewById(R.id.button_plus_minus).setOnClickListener(v -> {
            setOperation(Values.OPER_PLUS.getValue());
        });
        findViewById(R.id.button_percent).setOnClickListener(v -> {
            setOperation(Values.OPER_PERCENT.getValue());
        });
        findViewById(R.id.button_equally).setOnClickListener(v -> {
            setOperation(Values.OPER_EQ.getValue());
        });
        findViewById(R.id.button_c).setOnClickListener(v -> {
            clearEverything();
        });
    }

    private void sendAndShowData(String value) {
        data.setScreenText(value);
        data.setHistoryText(value);
        setTextOnScreen(data.getScreenText().toString());
        setTextOnHistory(data.getHistoryText().toString());
    }

    private void setOperation(String value) {
        data.setHistoryText(value);
        setTextOnHistory(data.getHistoryText().toString());

        try {
            data.setOperand(Double.parseDouble(data.getScreenText().toString()));
        } catch (NumberFormatException e) {
            setTextOnScreen(data.getResult().toString());
        }

        if (value.equals("%%")) {
            result(value);
            data.setOperand(data.getResult());
        } else if (data.isOperationFirst()) {
            data.setResult(data.getOperand());
            data.setOperand(ZERO);
            data.setOperationFirst(false);
            data.setOperationType(value);
        } else {
            result(value);
        }
        data.deleteScreenText();
    }

    private void result(String operationType) {
        switch (data.getOperationType()) {
            case "-":
                operation.subtraction();
                historyLogic(operationType);
                break;
            case "+":
                operation.sum();
                historyLogic(operationType);
                break;
            case "*":
                operation.multiplication();
                historyLogic(operationType);
                break;
            case "/":
                operation.division();
                historyLogic(operationType);
                break;
            case "%%":
                operation.percent();
                historyLogic(operationType);
                break;
            case "=":
                historyLogic(operationType);
                break;
            default:
                break;
        }
    }

    private void historyLogic(String operationType) {
        setTextOnScreen(data.getResult().toString());
        data.setOperationType(operationType);

        if (operationType.equals("=") || operationType.equals("%%")) {
            data.deleteHistoryText();
            data.setHistoryText(data.getResult().toString());
        } else {
            setTextOnHistory(data.getHistoryText().toString());
        }
    }

    private void setTextOnScreen(String text) {
        screen.setText(String.format(Locale.ENGLISH, text));
    }

    private void setTextOnHistory(String text) {
        history.setText(String.format(Locale.ENGLISH, text));
    }

    private void clearEverything() {
        data.deleteHistoryText();
        data.deleteScreenText();
        data.setOperand(ZERO);
        data.setResult(ZERO);
        setTextOnScreen(ZERO.toString());
        setTextOnHistory(ZERO.toString());
        data.setOperationFirst(true);
    }

}