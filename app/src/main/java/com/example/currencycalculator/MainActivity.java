package com.example.currencycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    final static String resultKey = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        ReceiveDataAsyncTask receiveDataAsyncTask = new ReceiveDataAsyncTask(this);
        receiveDataAsyncTask.execute();
        setContentView(R.layout.activity_main);
    }

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView result = (TextView) findViewById(R.id.Result);
        outState.putString(resultKey, result.getText().toString());

        super.onSaveInstanceState(outState);
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String resultString = savedInstanceState.getString(resultKey);
        TextView result = (TextView) findViewById(R.id.Result);
        result.setText(resultString);
    }

    // Метод обработки нажатия на левую кнопку
    public void convertEURtoRUB(View view) {
        // Получаем текстовое поле в текущей Activity
        EditText editText = (EditText) findViewById(R.id.enterNumber);
        // Получаем текст данного текстового поля
        String enteredNumber = editText.getText().toString().replace(",",".");
        if (enteredNumber == null || enteredNumber.isEmpty()
                || !enteredNumber.matches("((\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            TextView result = findViewById(R.id.Result);
            result.setText("");
            return;
        }

        TextView textViewNumber = findViewById(R.id.textViewNumber);
        String number = textViewNumber.getText().toString().replace(",",".");
        if (number == null || number.isEmpty()
                || !number.matches("((\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            TextView result = findViewById(R.id.Result);
            result.setText("");
            return;
        }

        double computedResult = Double.parseDouble(enteredNumber) * Double.parseDouble(number);
        TextView result = findViewById(R.id.Result);
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        result.setText(df.format(computedResult));
    }

    // Метод обработки нажатия на правую кнопку
    public void convertRUBtoEUR(View view) {
        // Получаем текстовое поле в текущей Activity
        EditText editText = (EditText) findViewById(R.id.enterNumber);
        // Получае текст данного текстового поля
        String enteredNumber = editText.getText().toString().replace(",",".");
        if (enteredNumber == null || enteredNumber.isEmpty()
                || !enteredNumber.matches("((\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            TextView result = findViewById(R.id.Result);
            result.setText("");
            return;
        }

        TextView textViewNumber = findViewById(R.id.textViewNumber);
        String number = textViewNumber.getText().toString().replace(",",".");
        if (number == null || number.isEmpty()
                || !number.matches("((\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            TextView result = findViewById(R.id.Result);
            result.setText("");
            return;
        }

        double computedResult = Double.parseDouble(enteredNumber) / Double.parseDouble(number);
        TextView result = findViewById(R.id.Result);

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        result.setText(df.format(computedResult));
    }
}
