package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private TextView weatherData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        weatherData =(TextView) findViewById(R.id.tv_weather_data);
        Intent intent = getIntent();

        if(intent.hasExtra("Weather")){

            weatherData.setText(intent.getExtras().getString("Weather"));

        }

    }
}