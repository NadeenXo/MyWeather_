package com.example.myweather_

object WeatherUtil {
    fun getWeatherName(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45 -> "Fog"
            48 -> "depositing rime fog"
            51 -> "Drizzle light intensity"
            53 -> "Drizzle moderate intensity"
            55 -> "Drizzle dense intensity"
            56 -> "Freezing drizzle light intensity"
            57 -> "Freezing drizzle dense intensity"
            61 -> "Slight rain intensity"
            63 -> "Moderate rain intensity"
            65 -> "Heavy rain intensity"
            66 -> "Light freezing rain intensity"
            67 -> "Heavy freezing rain intensity"
            71 -> "Slight snow fall intensity"
            73 -> "Moderate snow fall intensity"
            75 -> "Heavy snow fall intensity"
            77 -> "Snow grains"
            80 -> "Slight rain showers"
            81 -> "Moderate rain showers"
            82 -> "Violent rain showers"
            85 -> "Slight snow showers"
            86 -> "Heavy snow showers"
            95 -> "Thunderstorm"
            96 -> "Thunderstorm with slight hail"
            99 -> "Thunderstorm with heavy hail"
            else -> ""
        }
    }

    fun getWeatherImage(weatherCode: Int, isDay: Boolean): Int {
        when (weatherCode) {
            0 -> {
                return if (isDay) R.drawable.clear_sky_day else R.drawable.clear_sky_night
            }

            1 -> {
                return if (isDay) R.drawable.mainly_clear_day else R.drawable.mainly_clear_night
            }

            2 -> {
                return if (isDay) R.drawable.partly_cloudy_day else R.drawable.partly_cloudy_night
            }

            3 -> {
                return if (isDay) R.drawable.overcast_day else R.drawable.overcast_night
            }

            45 -> {
                return if (isDay) R.drawable.fog_day else R.drawable.fog_night
            }

            48 -> {
                return if (isDay) R.drawable.depositing_rime_fog_day else R.drawable.depositing_rime_fog_night
            }

            51 -> {
                return if (isDay) R.drawable.drizzle_light_day else R.drawable.drizzle_light_night
            }

            53 -> {
                return if (isDay) R.drawable.drizzle_moderate_day else R.drawable.drizzle_moderate_night
            }

            55 -> {
                return if (isDay) R.drawable.drizzle_intensity_day else R.drawable.drizzle_intensity_night
            }

            56 -> {
                return if (isDay) R.drawable.freezing_drizzle_light_day else R.drawable.freezing_drizzle_light_night
            }

            57 -> {
                return if (isDay) R.drawable.freezing_drizzle_intensity_day else R.drawable.freezing_drizzle_intensity_night
            }

            61 -> {
                return if (isDay) R.drawable.rain_slight_day else R.drawable.rain_slight_night
            }

            63 -> {
                return if (isDay) R.drawable.rain_moderate_day else R.drawable.rain_moderate_night
            }

            65 -> {
                return if (isDay) R.drawable.rain_intensity_day else R.drawable.rain_intensity_night
            }

            66 -> {
                return if (isDay) R.drawable.freezing_loght_day else R.drawable.freezing_light_night
            }

            67 -> {
                return if (isDay) R.drawable.freezing_heavy_day else R.drawable.freezing_heavy_night
            }

            71 -> {
                return if (isDay) R.drawable.snow_fall_light_day else R.drawable.snow_fall_light_night
            }

            73 -> {
                return if (isDay) R.drawable.snow_fall_moderate_day else R.drawable.snow_fall_moderate_night
            }

            75 -> {
                return if (isDay) R.drawable.snow_fall_intensity_day else R.drawable.snow_fall_intensity_night
            }

            77 -> {
                return if (isDay) R.drawable.snow_grains_day else R.drawable.snow_grains_night
            }

            80 -> {
                return if (isDay) R.drawable.rain_shower_slight_day else R.drawable.rain_shower_slight_night
            }

            81 -> {
                return if (isDay) R.drawable.rain_shower_moderate_day else R.drawable.rain_shower_moderate_night
            }

            82 -> {
                return if (isDay) R.drawable.rain_shower_violent_day else R.drawable.rain_shower_violent_night
            }

            85 -> {
                return if (isDay) R.drawable.snow_shower_slight_day else R.drawable.snow_shower_slight_night
            }

            86 -> {
                return if (isDay) R.drawable.snow_shower_heavy_day else R.drawable.snow_shower_heavy_night
            }

            95 -> {
                return if (isDay) R.drawable.thunderstrom_slight_or_moderate_day else R.drawable.thunderstrom_slight_or_moderate_night
            }

            96 -> {
                return if (isDay) R.drawable.thunderstrom_with_slight_hail_day else R.drawable.thunderstrom_with_slight_hail_night
            }

            99 -> {
                return if (isDay) R.drawable.thunderstrom_with_heavy_hail_day else R.drawable.thunderstrom_with_heavy_hail_night
            }
        }
        return R.drawable.clear_sky_day
    }
}
