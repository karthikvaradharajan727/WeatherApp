package com.karthik.wheaterapp

import org.json.JSONObject
import org.junit.Test

/**
 * WeatherViewModelTest local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WeatherViewModelTest {
    private val jsonObject =
        JSONObject("{\"coord\":{\"lon\":80.2192,\"lat\":12.9772},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":308.17,\"feels_like\":312.38,\"temp_min\":304.83,\"temp_max\":308.17,\"pressure\":1001,\"humidity\":46},\"visibility\":6000,\"wind\":{\"speed\":1.03,\"deg\":230},\"clouds\":{\"all\":40},\"dt\":1623329603,\"sys\":{\"type\":1,\"id\":9218,\"country\":\"IN\",\"sunrise\":1623283948,\"sunset\":1623330291},\"timezone\":19800,\"id\":1465825,\"name\":\"Madipakkam\",\"cod\":200}")

    @Test
    fun jsonModelTest() {
        val model = WeatherViewModel.jsonModel(jsonObject)
        assert(model.placeName == jsonObject.getString("name"))
        assert(model.placeName != "Madipakkam1")
        assert(
            model.description == jsonObject.getJSONArray("weather").getJSONObject(0)
                .getString("description")
        )
        assert(
            model.description != "description1"
        )
        assert(
            model.windDegree == jsonObject.getJSONObject("wind").getInt("deg")
        )
        assert(
            model.windDegree != 0
        )
        assert(
            model.windSpeed == jsonObject.getJSONObject("wind").getDouble("speed")
        )
        assert(
            model.windSpeed != 9000.0
        )
        assert(
            model.cloudy == jsonObject.getJSONObject("clouds").getInt("all")
        )
        assert(
            model.cloudy != 90
        )
        assert(
            model.temperatureNow == jsonObject.getJSONObject("main").getDouble("temp")
        )
        assert(
            model.temperatureNow != 45.0
        )
        assert(
            model.temperatureMax == jsonObject.getJSONObject("main").getDouble(
                "temp_max"
            )
        )
        assert(
            model.temperatureMax != 41.0
        )
        assert(
            model.temperatureMin == jsonObject.getJSONObject("main").getDouble(
                "temp_min"
            )
        )
        assert(
            model.temperatureMin != 45.0
        )
    }
}