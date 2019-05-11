package com.example.currencycalculator;

import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class ReceiveDataAsyncTask extends AsyncTask<Void, Void, String> {
    private MainActivity mainActivity;
    public ReceiveDataAsyncTask(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Void... noargs) {
        try {
            URL url = new URL("http://data.fixer.io/api/latest?access_key=cfb3c3a54e8d4699e9d2ef1be848b3f6");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                int responceCode = urlConnection.getResponseCode();
                if (responceCode != 200) {
                    return "";
                }
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String rub = readStream(in);
                return rub;
            } finally {
                urlConnection.disconnect();
            }
        }
        catch (IOException ioException){
            return "";
        }
    }

    private String readStream(InputStream in) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(new InputStreamReader(in)).getAsJsonObject();
        JsonObject rates = jsonObject.get("rates").getAsJsonObject();
        Double rub = rates.get("RUB").getAsDouble();
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(rub);
    }

    @Override
    protected void onPostExecute(String result) {
        TextView textViewNumber = this.mainActivity.findViewById(R.id.textViewNumber);
        if (result == null || result.isEmpty()
                || !result.matches("((\\+)?[0-9]+(\\,[0-9]+)?)+")){
            textViewNumber.setText("Нет данных:(");
        }
        else {
            textViewNumber.setText(result);
        }
    }
}


