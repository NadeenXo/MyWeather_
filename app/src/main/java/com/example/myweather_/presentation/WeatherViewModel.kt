package com.example.myweather_.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather_.domain.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class WeatherViewModel(
    private val fusedClient: FusedLocationProviderClient,
    private val weatherRepository: WeatherRepository,
    private val context: Context
) : ViewModel() {

    private val _locationName = mutableStateOf("Fetching location...")
    val locationName: State<String> = _locationName

    private val _weather = MutableStateFlow<WeatherUiModel?>(null)
    val weather: StateFlow<WeatherUiModel?> = _weather
    private val _isDay = MutableStateFlow<WeatherUiModel?>(null)
    val isDay: StateFlow<WeatherUiModel?> = _isDay

//    @SuppressLint("MissingPermission")
//    fun fetchLocation() {
//        fusedClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location != null) {
//                    val geocoder = Geocoder(context, Locale.getDefault())
//                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                    _locationName.value = address?.get(0)?.locality ?: "Unknown"
//                    fetchWeather(location.latitude, location.longitude)
//                } else {
//                    _locationName.value = "Location not available"
//                }
//            }
//            .addOnFailureListener {
//                _locationName.value = "Error getting location"
//            }
//    }

    @SuppressLint("MissingPermission")
    fun fetchLocation() {
        val cancellationTokenSource = CancellationTokenSource()

        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                Log.d("LocationDebug", "Lat: ${location.latitude}, Lon: ${location.longitude}")

                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    _locationName.value = address?.get(0)?.locality ?: "Unknown"
                } catch (e: Exception) {
                    e.printStackTrace()
                    _locationName.value = "Error decoding location"
                }

                fetchWeather(location.latitude, location.longitude)
            } else {
                Log.w("LocationDebug", "Location is null")
                _locationName.value = "Location not available"
            }
        }.addOnFailureListener { e ->
            Log.e("LocationDebug", "Failed to get location", e)
            _locationName.value = "Error getting location"
        }
    }


    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val dto = weatherRepository.getWeather(lat, lon)
                val uiModel = dto.toUiModel()
                _weather.value = uiModel

                Log.i("TAG", "fetchWeather: {${_weather.value?.temperature}}")
            } catch (e: Exception) {
                _locationName.value = "Error loading weather: $e"
            }
        }
    }

}
