package com.jubair.nsu.cse486.sec1.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val CITY: String = "dhaka,bd" //location
    val API: String = "b69ae6c18fc8c4b601ac4b09620b13c8" // Use API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val location : TextView = findViewById(R.id.showlocation);
        val temp : TextView = findViewById(R.id.showtemp);
        val weather : TextView = findViewById(R.id.showWeather);
        val wind : TextView = findViewById(R.id.showWind);
        val humidity : TextView = findViewById(R.id.showHumidity);
        val pressure : TextView = findViewById(R.id.showPressure);
        val sunrise : TextView = findViewById(R.id.showSunrise);
        val sunset : TextView = findViewById(R.id.showSunset);



    }






}