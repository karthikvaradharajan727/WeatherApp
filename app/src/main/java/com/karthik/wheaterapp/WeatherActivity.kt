package com.karthik.wheaterapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karthik.wheaterapp.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
    private val viewBinding: ActivityWeatherBinding by lazy {
        ActivityWeatherBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewModel.data.observe(this, {
            viewBinding.placeTextView.text = "Place: ${it.placeName}"
            viewBinding.descriptionTextView.text = "Description: ${it.description}"
            viewBinding.cloudyTextView.text = "${it.cloudy}% cloudy"
            viewBinding.windSpeedTextView.text = "Wind Speed:${it.windSpeed}"
            viewBinding.windDegreeTextView.text = "Wind Degree:${it.windDegree}"
            viewBinding.temperatureNowTextView.text = "Temperature : ${it.temperatureNow}C"
            viewBinding.temperatureMaxTextView.text = "Temperature Max: ${it.temperatureMax}C"
            viewBinding.temperatureMinTextView.text = "Temperature Min: ${it.temperatureMin}C"
        })
        checkPermission()
    }

    override fun onDestroy() {
        viewModel.data.removeObservers(this)
        super.onDestroy()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            println("No permission")
            return
        }
        viewModel.fetchWeather {
            showErrorDialog()
        }
    }

    /*
    * Show the error dialog
    * */
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage("Error occurs kindly retry again")
            .setPositiveButton(
                "Retry"
            ) { _, _ -> checkPermission() }
            .setCancelable(false)
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkPermission()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}