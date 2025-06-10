package com.example.myweather_.data

import com.example.myweather_.data.model.WeatherDataModel
import com.example.myweather_.domain.model.WeatherDomainModel

fun WeatherDataModel.toDomain(): WeatherDomainModel {
    return WeatherDomainModel(
        temperature = current?.temperature ?: 0.0,
        humidity = current?.humidity ?: 0.0,
        uvIndex = current?.uvIndex ?: 0.0,
        isDay = (current?.isDay ?: 0) == 1,
        rain = current?.rain ?: 0.0,
        weatherCode = current?.weatherCode ?: 0,
        pressure = current?.pressure ?: 0.0,
        windSpeed = current?.windSpeed ?: 0.0,

        dailyDates = daily?.dates ?: emptyList(),
        dailyMaxTemps = daily?.maxTemps ?: emptyList(),
        dailyMinTemps = daily?.minTemps ?: emptyList(),
        dailyWeatherCodes = daily?.weatherCodes ?: emptyList(),

        hourlyTimes = hourly?.times ?: emptyList(),
        hourlyTemperatures = hourly?.temperatures ?: emptyList(),
        hourlyWeatherCodes = hourly?.weatherCodes ?: emptyList(),
        feelsLike = hourly?.feelsLike ?: 0.0

    )
}
