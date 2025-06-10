package com.example.myweather.data.remote


import com.example.myweather_.data.model.WeatherDataModel
import com.example.myweather_.data.remote.WeatherApi
import com.example.myweather_.data.remote.WeatherHttpClient
import io.ktor.client.request.*
import io.ktor.client.call.*

class WeatherRemoteDataSource : WeatherApi {
    override suspend fun getWeatherData(lat: Double, lon: Double): WeatherDataModel {
        return WeatherHttpClient.client.get("https://api.open-meteo.com/v1/forecast") {
            parameter("latitude", lat)
            parameter("longitude", lon)
            parameter("current", listOf(
                "temperature_2m",
                "relative_humidity_2m",
                "uv_index",
                "is_day",
                "rain",
                "weather_code",
                "surface_pressure",
                "wind_speed_10m"
            ).joinToString(","))

            parameter("daily", listOf(
                "temperature_2m_max",
                "temperature_2m_min",
                "weather_code"
            ).joinToString(","))

            parameter("hourly", listOf(
                "temperature_2m",
                "weather_code"
            ).joinToString(","))

            parameter("timezone", "auto")
            parameter("forecast_days", 8)
        }.body()
    }
}


