package com.example.myweather_.presentation


import android.icu.text.SimpleDateFormat
import com.example.myweather_.WeatherUtil
import com.example.myweather_.domain.model.WeatherDomainModel
import java.util.Calendar

fun WeatherDomainModel.toUiModel(): WeatherUiModel {
    val dailyForecastItems = (0 until dailyDates.size).map { index ->
        DailyUiItem(
            date = dailyDates.getOrNull(index) ?: "Unknown",
            max = dailyMaxTemps.getOrNull(index)?.toString() ?: "N/A",
            min = dailyMinTemps.getOrNull(index)?.toString() ?: "N/A",
            iconRes = WeatherUtil.getWeatherImage(
                dailyWeatherCodes.getOrNull(index) ?: 0,
                isDay
            )
        )
    }

    val hourlyForecastItems = (0 until hourlyTimes.size).map { index ->
        val rawTime = hourlyTimes.getOrNull(index) ?: "N/A"
        val formattedTime = if (rawTime != "N/A") {
            val parts = rawTime.split("T")[1].split(":")
            "${parts[0]}:${parts[1]}"
        } else {
            "N/A"
        }

        HourlyUiItem(
            time = formattedTime,
            temp = "${hourlyTemperatures.getOrNull(index)?.toInt()}°C",
            iconRes = WeatherUtil.getWeatherImage(
                hourlyWeatherCodes.getOrNull(index) ?: 0,
                isDay = isDay
            )
        )
    }

    val forecastItems = mapToDailyUiItems(
        dailyDates = dailyDates,
        dailyMaxTemps = dailyMaxTemps,
        dailyMinTemps = dailyMinTemps,
        dailyWeatherCodes = dailyWeatherCodes,
        isDay = isDay
    )
    return WeatherUiModel(
        temperature = "$temperature°C",
        humidity = "$humidity%",
        uvIndex = "$uvIndex",
        rain = "$rain%",
        pressure = "$pressure hPa",
        feelsLike = "$feelsLike°C",
        windSpeed = "$windSpeed km/h",
        weatherName = WeatherUtil.getWeatherName(weatherCode),
        weatherIconRes = WeatherUtil.getWeatherImage(weatherCode, isDay),
        isDay = isDay,
        dailyForecast = forecastItems,
        hourlyForecast = hourlyForecastItems
    )


}

fun mapToDailyUiItems(
    dailyDates: List<String>,
    dailyMaxTemps: List<Double>,
    dailyMinTemps: List<Double>,
    dailyWeatherCodes: List<Int>,
    isDay: Boolean
): List<DailyUiItem> {
    val dayNames = dailyDates.map { dateStr ->
        val cal = Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
        }
        when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            else -> "??"
        }
    }

    return (0 until dailyDates.size).map { index ->
        DailyUiItem(
            date = dayNames.getOrNull(index) ?: "Unknown",
            max = dailyMaxTemps.getOrNull(index)?.toInt().toString(),
            min = dailyMinTemps.getOrNull(index)?.toInt().toString(),
            iconRes = WeatherUtil.getWeatherImage(
                weatherCode = dailyWeatherCodes.getOrNull(index) ?: 0,
                isDay = isDay
            )
        )
    }
}