package com.example.myweather_.data

import com.example.myweather_.data.remote.WeatherApi
import com.example.myweather_.domain.WeatherRepository
import com.example.myweather_.domain.model.WeatherDomainModel


class WeatherRepositoryImpl(
    private val apiService: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): WeatherDomainModel {
        val weatherDto = apiService.getWeatherData(
            lat = lat,
            lon = lon
        )
//        val currentWeather = weatherDto.current ?: throw Exception("Missing current weather data")
        return weatherDto.toDomain()
    }
}
