package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView editText;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout for immersive design
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle system insets for immersive layout (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the display TextView
        editText = findViewById(R.id.editText);

        // Set listeners for number buttons
        findViewById(R.id.button0).setOnClickListener(numberClickListener);
        findViewById(R.id.button1).setOnClickListener(numberClickListener);
        findViewById(R.id.button2).setOnClickListener(numberClickListener);
        findViewById(R.id.button3).setOnClickListener(numberClickListener);
        findViewById(R.id.button4).setOnClickListener(numberClickListener);
        findViewById(R.id.button5).setOnClickListener(numberClickListener);
        findViewById(R.id.button6).setOnClickListener(numberClickListener);
        findViewById(R.id.button7).setOnClickListener(numberClickListener);
        findViewById(R.id.button8).setOnClickListener(numberClickListener);
        findViewById(R.id.button9).setOnClickListener(numberClickListener);

        // Set listeners for operator buttons
        findViewById(R.id.buttonAdd).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonMinus).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonMultiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonDivide).setOnClickListener(operatorClickListener);

        // Set listeners for special buttons
        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculate());
        findViewById(R.id.buttonDot).setOnClickListener(v -> addDecimal());
        findViewById(R.id.buttonPercentage).setOnClickListener(v -> calculatePercentage());
        findViewById(R.id.buttonBackspace).setOnClickListener(v -> backspace());
    }

    // Listener for number buttons
    private final View.OnClickListener numberClickListener = v -> {
        Button button = (Button) v;
        if (isOperatorPressed) {
            currentInput = "";
            isOperatorPressed = false;
        }
        currentInput += button.getText().toString();
        editText.setText(currentInput);
    };

    // Listener for operator buttons
    private final View.OnClickListener operatorClickListener = v -> {
        Button button = (Button) v;
        operator = button.getText().toString();
        firstNumber = Double.parseDouble(currentInput);
        isOperatorPressed = true;
    };

    // Clear the display and reset the calculator
    private void clear() {
        currentInput = "";
        operator = "";
        firstNumber = 0;
        editText.setText("0");
    }

    // Handle decimal point input
    private void addDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            editText.setText(currentInput);
        }
    }

    // Calculate the result of the operation
    private void calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;

        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    editText.setText("Error");
                    return;
                }
                break;
        }

        currentInput = String.valueOf(result);
        editText.setText(currentInput);
        operator = "";
        isOperatorPressed = true;
    }

    // Calculate percentage
    private void calculatePercentage() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput) / 100;
            currentInput = String.valueOf(value);
            editText.setText(currentInput);
        }
    }

    // Handle backspace (delete last character)
    private void backspace() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                editText.setText("0");
            } else {
                editText.setText(currentInput);
            }
        }
    }
}
