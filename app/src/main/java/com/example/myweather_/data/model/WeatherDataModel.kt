package com.example.myweather_.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataModel(
    @SerialName("current") val current: CurrentWeatherDto? = null,
    @SerialName("daily") val daily: DailyWeatherDto? = null,
    @SerialName("hourly") val hourly: HourlyWeatherDto? = null
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m") val temperature: Double? = null,
    @SerialName("relative_humidity_2m") val humidity: Double? = null,
    @SerialName("uv_index") val uvIndex: Double? = null,
    @SerialName("is_day") val isDay: Int? = null,
    @SerialName("rain") val rain: Double? = null,
    @SerialName("weather_code") val weatherCode: Int? = null,
    @SerialName("surface_pressure") val pressure: Double? = null,
    @SerialName("wind_speed_10m") val windSpeed: Double? = null,
)

@Serializable
data class DailyWeatherDto(
    @SerialName("time") val dates: List<String> = emptyList(),
    @SerialName("temperature_2m_max") val maxTemps: List<Double> = emptyList(),
    @SerialName("temperature_2m_min") val minTemps: List<Double> = emptyList(),
    @SerialName("weather_code") val weatherCodes: List<Int> = emptyList()
)

@Serializable
data class HourlyWeatherDto(
    @SerialName("time") val times: List<String> = emptyList(),
    @SerialName("temperature_2m") val temperatures: List<Double> = emptyList(),
    @SerialName("weather_code") val weatherCodes: List<Int> = emptyList(),
    @SerialName("apparent_temperature") val feelsLike: Double? = null

)
