package com.karthik.wheaterapp

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import org.json.JSONObject

/*
* Sub class of AndroidViewModel because
* context needed to fetch location
* */
class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        /*
        * convert Json to model
        * @params jsonObject    Response from API
        * */
        fun jsonModel(jsonObject: JSONObject) =
            WeatherModel(
                placeName = jsonObject.getString("name"),
                description = jsonObject.getJSONArray("weather").getJSONObject(0)
                    .getString("description"),
                windDegree = jsonObject.getJSONObject("wind").getInt("deg"),
                windSpeed = jsonObject.getJSONObject("wind").getDouble("speed"),
                cloudy = jsonObject.getJSONObject("clouds").getInt("all"),
                temperatureNow = jsonObject.getJSONObject("main").getDouble("temp"),
                temperatureMax = jsonObject.getJSONObject("main").getDouble("temp_max"),
                temperatureMin = jsonObject.getJSONObject("main").getDouble("temp_min")
            )

    }

    private val context: Context = application
    private val repository = WeatherRepository()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    val data = MutableLiveData<WeatherModel>()

    /*
    * start fetching the weather start with location and weather API
    * @params error     Error callback
    * */
    fun fetchWeather(error: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            error()
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { it ->
            it?.let { location ->
                repository.getWeather(location.latitude, location.longitude, {
                    data.value = jsonModel(it)
                }, error)
            }
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            error()
        }
    }

    /*
    * Class which has data are needed which
    * helps to create data layer and UI has only data needed
    * */
    data class WeatherModel(
        val placeName: String,
        val description: String,
        val windSpeed: Double,
        val windDegree: Int,
        val cloudy: Int,
        val temperatureNow: Double,
        val temperatureMax: Double,
        val temperatureMin: Double
    )
}