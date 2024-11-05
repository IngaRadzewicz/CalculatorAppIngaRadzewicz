// src/main/java/com/example/calculatorappingaradzewicz/MainActivity.java
package com.example.calculatorappingaradzewicz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double firstValue = Double.NaN;
    private double secondValue;
    private char currentOperation;
    private double memoryValue = 0; // Memory value for MC, MR, MS, M+, M- operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Set up button listeners
        setNumberButtonListeners();
        setOperationButtonListeners();
        setMemoryButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberListener = view -> {
            Button button = (Button) view;
            display.append(button.getText().toString());
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberListener);
        }

        findViewById(R.id.btnDecimal).setOnClickListener(view -> display.append("."));
    }

    private void setOperationButtonListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(view -> setOperation('+'));
        findViewById(R.id.btnSubtract).setOnClickListener(view -> setOperation('-'));
        findViewById(R.id.btnMultiply).setOnClickListener(view -> setOperation('*'));
        findViewById(R.id.btnDivide).setOnClickListener(view -> setOperation('/'));

        findViewById(R.id.btnEquals).setOnClickListener(view -> compute());
        findViewById(R.id.btnClear).setOnClickListener(view -> display.setText("0"));
        findViewById(R.id.btnClearEntry).setOnClickListener(view -> display.setText("0"));
        findViewById(R.id.btnBackspace).setOnClickListener(view -> backspace());
        findViewById(R.id.btnSign).setOnClickListener(view -> toggleSign());
        findViewById(R.id.btnSqrt).setOnClickListener(view -> sqrtOperation());
    }

    private void setMemoryButtonListeners() {
        findViewById(R.id.btnMC).setOnClickListener(view -> memoryValue = 0);
        findViewById(R.id.btnMR).setOnClickListener(view -> display.setText(String.valueOf(memoryValue)));
        findViewById(R.id.btnMS).setOnClickListener(view -> memoryValue = Double.parseDouble(display.getText().toString()));
        findViewById(R.id.btnMPlus).setOnClickListener(view -> memoryValue += Double.parseDouble(display.getText().toString()));
        findViewById(R.id.btnMMinus).setOnClickListener(view -> memoryValue -= Double.parseDouble(display.getText().toString()));
    }

    private void setOperation(char operation) {
        firstValue = Double.parseDouble(display.getText().toString());
        currentOperation = operation;
        display.setText("");
    }

    private void compute() {
        if (!Double.isNaN(firstValue)) {
            secondValue = Double.parseDouble(display.getText().toString());
            double result;
            switch (currentOperation) {
                case '+':
                    result = firstValue + secondValue;
                    break;
                case '-':
                    result = firstValue - secondValue;
                    break;
                case '*':
                    result = firstValue * secondValue;
                    break;
                case '/':
                    result = firstValue / secondValue;
                    break;
                default:
                    return;
            }
            display.setText(String.valueOf(result));
            firstValue = Double.NaN;
        }
    }

    private void toggleSign() {
        double value = Double.parseDouble(display.getText().toString());
        display.setText(String.valueOf(-value));
    }

    private void sqrtOperation() {
        double value = Double.parseDouble(display.getText().toString());
        display.setText(String.valueOf(Math.sqrt(value)));
    }

    private void backspace() {
        String text = display.getText().toString();
        if (text.length() > 0) {
            display.setText(text.substring(0, text.length() - 1));
        }
    }

    // Method for percentage operation
    private void percentOperation() {
        double value = Double.parseDouble(display.getText().toString());
        display.setText(String.valueOf(value / 100));
    }

    // Method for reciprocal operation
    private void reciprocalOperation() {
        double value = Double.parseDouble(display.getText().toString());
        if (value != 0) {
            display.setText(String.valueOf(1 / value));
        } else {
            display.setText("Error"); // Display error if dividing by zero
        }
    }
}
