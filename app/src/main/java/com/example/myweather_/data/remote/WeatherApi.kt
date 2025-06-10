package com.example.myweather_.data.remote

import com.example.myweather_.data.model.WeatherDataModel

interface WeatherApi {
    suspend fun getWeatherData(
        lat: Double,
        lon: Double
    ): WeatherDataModel
}
