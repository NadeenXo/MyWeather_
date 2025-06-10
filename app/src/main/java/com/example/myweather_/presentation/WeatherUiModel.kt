package com.example.myweather_.presentation

data class WeatherUiModel(
    val temperature: String,
    val humidity: String,
    val uvIndex: String,
    val pressure: String,
    val feelsLike: String,
    val windSpeed: String,
    val weatherName: String,
    val weatherIconRes: Int,
    val isDay: Boolean,

    val dailyForecast: List<DailyUiItem> = emptyList(),
    val hourlyForecast: List<HourlyUiItem> = emptyList(),
    val rain: String
)

data class DailyUiItem(
    val date: String,
    val max: String,
    val min: String,
    val iconRes: Int
)

data class HourlyUiItem(
    val time: String,
    val temp: String,
    val iconRes: Int
)

