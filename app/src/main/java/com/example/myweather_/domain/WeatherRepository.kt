package com.example.myweather_.domain

import com.example.myweather_.domain.model.WeatherDomainModel

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherDomainModel
}