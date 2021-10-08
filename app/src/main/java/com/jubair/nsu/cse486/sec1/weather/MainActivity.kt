package com.jubair.nsu.cse486.sec1.weather

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil


class MainActivity : AppCompatActivity() {

    val CITY: String = "Dhaka" //location
    val API_KEY: String = "b69ae6c18fc8c4b601ac4b09620b13c8" // Use API key


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Color of Status Bar
        window.statusBarColor= Color.parseColor("#086FC1")

        val location : TextView = findViewById(R.id.showlocation);
        location.text = CITY

        // Search Location
        val search : SearchView =findViewById(R.id.searchView)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                location.text = query.toString()
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                return true;
            }
            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })

        //Get Weather Information for a particular city
        getJsonData()
    }

    private fun getJsonData()
    {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${CITY}&appid=${API_KEY}"

        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                Response.Listener { response ->
                    // Display the first 500 characters of the response string.
                    setValues(response)
                },
                Response.ErrorListener {Toast.makeText(this,"ERROR", Toast.LENGTH_LONG).show() })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    private fun setValues(response:JSONObject)
    {
        val temp : TextView = findViewById(R.id.showtemp)
        val weather : TextView = findViewById(R.id.showWeather)
        val wind : TextView = findViewById(R.id.showWind);
        val humidity : TextView = findViewById(R.id.showHumidity);
        val pressure : TextView = findViewById(R.id.showPressure);
        val maxTemp : TextView = findViewById(R.id.max);
        val minTemp : TextView = findViewById(R.id.min);
        val sunrise : TextView = findViewById(R.id.showSunrise);
        val sunset : TextView = findViewById(R.id.showSunset);
        val weatherImage : ImageView = findViewById(R.id.weatherImg);


        // Get Temperature
        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text="${tempr}°C"

        // Get Weather
        weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")

        // Shows weather picture with changes to weather
        if (weather.text == "Haze" || weather.text == "Few Clouds"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.haze))
        }
        else if (weather.text == "Clouds" || weather.text == "Overcast Clouds" || weather.text == "Broken Clouds" || weather.text == "Scattered Clouds"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cloudy))
        }
        else if (weather.text == "Rainy"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rainy))
        }
        else if (weather.text == "Clear Sky"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.clear))
        }
        else if (weather.text == "Fog"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fog))
        }
        else if (weather.text == "Snow"){
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.snow))
        }
        else{
            weatherImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.clear))
        }


        //Get Pressure and Humidity
        pressure.text=response.getJSONObject("main").getString("pressure")
        humidity.text=response.getJSONObject("main").getString("humidity")+"%"

        //Get Wind Speed
        var windSpeed =response.getJSONObject("wind").getString("speed")
        wind.text = "${windSpeed} km/h"

        //Get Min and Max Temperature
        var maxtemp=response.getJSONObject("main").getString("temp_max")
        maxtemp=((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()
        maxTemp.text=maxtemp+"°C"
        var mintemp=response.getJSONObject("main").getString("temp_min")
        mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
        minTemp.text=mintemp+"°C"

        //Get Sunrise and Sunset time
        var sunRise = response.getJSONObject("sys").getString("sunrise")
        var rise = sunRise.toLong() //Convert String to Long
        var sunSet = response.getJSONObject("sys").getString("sunset")
        var set = sunSet.toLong() //Convert String to Long

        sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(rise * 1000))
        sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(set * 1000))

    }








}