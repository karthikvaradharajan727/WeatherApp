package com.karthik.wheaterapp

import android.location.Location
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherRepository {
    /*
    * Get the weather data from the API
    * @params latitude      latitude of location
    * @params longitude     longitude of location
    * @params response      callback for the response API
    * @params error         error callback for the API
    * */
    fun getWeather(
        latitude: Double,
        longitude: Double,
        response: (JSONObject) -> Unit,
        error: () -> Unit
    ) {
        Retrofit.Builder().baseUrl(BuildConfig.weather_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherService::class.java)
            .getWeather(latitude, longitude)
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, res: Response<JsonObject>) {
                    response(JSONObject(res.body().toString()))
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    error()
                }
            })
    }

    interface WeatherService {
        @GET("data/2.5/weather")
        fun getWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") appId: String = BuildConfig.api_key,
            @Query("units") units: String = "metric"
        ): Call<JsonObject>
    }
}