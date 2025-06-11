package com.example.myweather_.presentation


import android.icu.text.SimpleDateFormat
import com.example.myweather_.WeatherUtil
import com.example.myweather_.domain.model.WeatherDomainModel
import java.util.Calendar

fun WeatherDomainModel.toUiModel(): WeatherUiModel {

    val forecastItems = mapToDailyUiItems(
        dailyDates = dailyDates,
        dailyMaxTemps = dailyMaxTemps,
        dailyMinTemps = dailyMinTemps,
        dailyWeatherCodes = dailyWeatherCodes,
        isDay = isDay
    )


    val hourlyForecastItems = filterTodayHourlyForecast(
        hourlyTimes = hourlyTimes,
        hourlyTemperatures = hourlyTemperatures,
        hourlyWeatherCodes = hourlyWeatherCodes,
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
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "None"
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

fun filterTodayHourlyForecast(
    hourlyTimes: List<String>,
    hourlyTemperatures: List<Double>,
    hourlyWeatherCodes: List<Int>,
    isDay: Boolean
): List<HourlyUiItem> {
    val now = Calendar.getInstance()
    val filtered = mutableListOf<HourlyUiItem>()

    for (i in hourlyTimes.indices) {
        val timeStr = hourlyTimes[i]
        val cal = Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(timeStr)
        }

        if (cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) &&
            cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)
        ) {

            if (cal.timeInMillis >= now.timeInMillis) {
                val formattedTime = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
                val temp = hourlyTemperatures.getOrNull(i)?.toInt().toString()
                val iconRes = WeatherUtil.getWeatherImage(
                    weatherCode = hourlyWeatherCodes.getOrNull(i) ?: 0,
                    isDay = isDay
                )

                filtered.add(
                    HourlyUiItem(
                        time = formattedTime,
                        temp = "$temp°C",
                        iconRes = iconRes
                    )
                )
            }
        }
    }

    return filtered.take(10)
}