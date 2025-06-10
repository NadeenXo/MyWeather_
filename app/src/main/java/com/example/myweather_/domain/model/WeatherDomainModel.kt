package com.example.myweather_.domain.model

data class WeatherDomainModel(
    val temperature: Double,
    val humidity: Double,
    val uvIndex: Double,
    val isDay: Boolean,
    val rain: Double,
    val weatherCode: Int,
    val pressure: Double,
    val feelsLike: Double,
    val windSpeed: Double,

    val dailyDates: List<String> = emptyList(),
    val dailyMaxTemps: List<Double> = emptyList(),
    val dailyMinTemps: List<Double> = emptyList(),
    val dailyWeatherCodes: List<Int> = emptyList(),

    val hourlyTimes: List<String> = emptyList(),
    val hourlyTemperatures: List<Double> = emptyList(),
    val hourlyWeatherCodes: List<Int> = emptyList()
)
