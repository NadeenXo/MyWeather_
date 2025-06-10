package com.example.myweather_.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather_.R
import com.example.myweather_.ui.theme.Black
import com.example.myweather_.ui.theme.BlackTransparent70
import com.example.myweather_.ui.theme.Blue
import com.example.myweather_.ui.theme.GrayTransparent66
import com.example.myweather_.ui.theme.White
import com.example.myweather_.ui.theme.WhiteTransparent70
import org.koin.androidx.compose.koinViewModel
import org.slf4j.MDC.put

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val locationName = viewModel.locationName.value.toString()
    val weatherState = viewModel.weather.collectAsState()
    val isDay = viewModel.isDay.toString().toBoolean()

    LaunchedEffect(Unit) {
        viewModel.fetchLocation()
    }
    Log.i("TAG", "WeatherScreen: $locationName")
    Log.i("TAG", "WeatherScreen: ${weatherState.value?.temperature}")

    val textColor = if (isDay) {
        Black
    } else {
        White
    }

    val gradientBrush = if (isDay) {
        Brush.verticalGradient(listOf(Blue, White))
    } else {
        Brush.verticalGradient(listOf(BlackTransparent70, Color(0xFF0D0C19)))
    }
    val cardBG = if (isDay) {
        WhiteTransparent70
    } else {
        BlackTransparent70
    }

//
//    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
//    val icons = listOf(
//        R.drawable.clear_sky_day, R.drawable.partly_cloudy_day, R.drawable.rain_intensity_day,
//        R.drawable.clear_sky_day, R.drawable.partly_cloudy_day, R.drawable.rain_intensity_day,
//        R.drawable.clear_sky_day
//    )
//    val minTemps = listOf(15, 17, 14, 16, 18, 13, 15)
//    val maxTemps = listOf(25, 27, 22, 24, 26, 23, 24)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { WeatherHeaderSection(textColor, weatherState, locationName) }
        item {
            WeatherStatsGrid(weather = weatherState.value, cardBG = cardBG, textColor = textColor)
        }
        item {
            TodayHourlyForecast(
                cardBG, textColor, weather = weatherState.value
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Next 7 days",
                    fontSize = 20.sp, color = textColor,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.padding(bottom = 12.dp))

                Column(
                    modifier = Modifier.background(
//                        color = WhiteTransparent70.copy(alpha = 0.1f),
                        color = cardBG,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                ) {
                    val forecastItems = weatherState.value?.dailyForecast ?: emptyList()

                    if (forecastItems.isNotEmpty()) {
                        Column(
                            modifier = Modifier.background(
                                color = cardBG,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                        ) {
                            val forecastList = forecastItems.drop(1).take(7)

                            forecastList.forEachIndexed { index, item ->
                                val isLastItem = index == forecastList.size - 1

                                Next7DayItem(
                                    items = item,
                                    showBorder = true,
                                    topRounded = index == 0,
                                    bottomRounded = isLastItem,
                                    textColor = textColor
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No forecast data",
                            color = textColor,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun WeatherHeaderSection(
    textColor: Color,
    weatherState: State<WeatherUiModel?>,
    locationName: String
) {
    val weather = weatherState.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Location Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.location),
                contentDescription = "Location",
                tint = textColor
            )
            Spacer(modifier = Modifier.padding(end = 4.dp))
            Text(
                text = locationName,
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        ShadowBlurBehindImage(
            weather?.weatherIconRes ?: R.drawable.ic_launcher_foreground
        )

        Spacer(modifier = Modifier.padding(bottom = 12.dp))

        // Temperature
        Text(
            text = weather?.temperature ?: "--°C",
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp
        )

        // Description
        Text(
            text = weather?.weatherName ?: "Loading...",
            color = textColor,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .background(
                    color = GrayTransparent66.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 8.dp, horizontal = 24.dp)
        ) {
            if (weather != null && weather.dailyForecast.isNotEmpty()) {
                TemperatureRange(
                    min = weather.dailyForecast[0].min,
                    max = weather.dailyForecast[0].max,
                    textColor = textColor
                )
            } else {
                TemperatureRange(min = "", max = "", textColor = textColor)
            }
        }
    }
}

@Composable
fun ShadowBlurBehindImage(weatherIconRes: Int) {
    Box(
        modifier = Modifier
            .height(200.dp)
//            .wrapContentWidth()
//            .align(Alignment.Center)

    ) {
        Box(

            modifier = Modifier
//                .fillMaxHeight()
                .matchParentSize()
                .clip(shape = CircleShape)
                .graphicsLayer {
                    translationY = 4f // vertical offset for shadow
                    alpha = 0.25f // make it transparent
                    scaleX = 2.5f
                    scaleY = 3f // slightly bigger for a nice glow effect
                    shadowElevation = 1f
                }
                .blur(5.dp) // Apply blur to simulate softness
        )

        // Actual image
        Image(
            painter = painterResource(id = weatherIconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
//            modifier = Modifier.matchParentSize()
        )
    }

}

@Composable
private fun TemperatureRange(min: String, max: String, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "↑ ${max}°C",
            color = textColor, fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(1.dp)
                .height(14.dp)
                .background(GrayTransparent66)
        )
        Text(
            text = "↓ ${min}°C",
            color = textColor, fontSize = 16.sp
        )
    }
}


@Composable
fun WeatherStatsGrid(
    weather: WeatherUiModel?,
    cardBG: Color,
    textColor: Color
) {
    val wind = weather?.windSpeed ?: "N/A"
    val humidity = weather?.humidity ?: "N/A"
    val uvIndex = weather?.uvIndex ?: "N/A"
    val pressure = weather?.pressure ?: "N/A"
    val feelsLike = weather?.feelsLike ?: "N/A"
    val rain = weather?.rain ?: "N/A"


    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherStatCard(wind, "Wind", R.drawable.fast_wind, cardBG, textColor)
            WeatherStatCard(humidity, "Humidity", R.drawable.humidity, cardBG, textColor)
            WeatherStatCard(rain, "Rain", R.drawable.rain, cardBG, textColor)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherStatCard(uvIndex, "UV Index", R.drawable.uv, cardBG, textColor)
            WeatherStatCard(
                pressure, "Pressure", R.drawable.arrow_pressure,
                cardBG,
                textColor
            )
            WeatherStatCard(feelsLike, "Feels Like", R.drawable.temperature, cardBG, textColor)
        }
    }
}

@Composable
fun WeatherStatCard(value: String, label: String, resID: Int, cardBG: Color, textColor: Color) {
    Card(
        modifier = Modifier
            .size(width = 108.dp, height = 115.dp)
            .defaultMinSize(minWidth = 108.dp, minHeight = 115.dp)
            .border(
                width = 1.dp,
                color = WhiteTransparent70.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(end = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBG
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(resID), contentDescription = null, tint = Blue)
            Text(text = value, style = MaterialTheme.typography.titleMedium, color = textColor)
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = textColor)
        }
    }
}

@Composable
fun TodayHourlyForecast(
    cardBG: Color,
    textColor: Color,
    weather: WeatherUiModel?
) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Today",
            fontSize = 20.sp,
            color = textColor,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // Safely take first 10 items or however many are available
            val hourlyData = weather?.hourlyForecast ?: emptyList()

            items(hourlyData.size) { index ->
                val item = hourlyData[index]

                Box(
                    modifier = Modifier
                        .width(88.dp)
                        .height(120.dp)
                        .padding(top = 10.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .border(
                                width = 1.dp,
                                color = WhiteTransparent70.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = cardBG),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.temp,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 16.dp),
                                color = textColor
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(
                                text = item.time,
                                fontSize = 16.sp,
                                color = textColor
                            )
                        }
                    }

                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .offset(y = -(20).dp)
                            .shadow(elevation = 40.dp, clip = false)
                    )
                }
            }
        }
    }
}



@Composable
fun Next7DayItem(
    items: DailyUiItem?,
    showBorder: Boolean,
    topRounded: Boolean = false,
    bottomRounded: Boolean = false,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (topRounded && bottomRounded) {
                    // Single item with full rounded shape
                    Modifier.border(
                        width = 1.dp,
                        color = GrayTransparent66.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                } else if (topRounded) {
                    // First item
                    Modifier.border(
                        width = 1.dp,
                        color = GrayTransparent66.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                } else if (bottomRounded) {
                    // Last item
                    Modifier.border(
                        width = 1.dp,
                        color = GrayTransparent66.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                } else if (showBorder) {
                    // Middle items
                    Modifier.border(
                        width = 1.dp,
                        color = GrayTransparent66.copy(alpha = 0.08f)
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 12.dp, vertical = 21.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = items?.date ?: "",
            Modifier.widthIn(min = 91.dp),
            fontSize = 16.sp,
            color = textColor
        )
        Image(
            painter = painterResource(id = items?.iconRes ?: R.drawable.clear_sky_day),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .weight(1f),
        )
        Box(contentAlignment = Alignment.CenterEnd) {
            TemperatureRange(items?.min ?: "", items?.max ?: "", textColor)
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 1222)
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen(koinViewModel())
}
