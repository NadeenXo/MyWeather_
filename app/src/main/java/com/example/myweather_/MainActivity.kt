package com.example.myweather_

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myweather_.presentation.WeatherScreen
import com.example.myweather_.presentation.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            MaterialTheme {
//                WeatherScreen(true)
//            }
            WeatherApp(koinViewModel())
        }

        if (permissions.any {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
//        else {
//            weatherViewModel.fetchLocation()
//        }


    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
//            grantResults.isNotEmpty() &&
//            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
//        ) {
//            weatherViewModel.fetchLocation()
//        }
//
//    }

}

//@Composable
//fun WeatherApp(mood: Int) {
//    val isDay = mood == 1
//    val colorScheme = if (isDay) LightColorScheme else DarkColorScheme
//
////    MaterialTheme(colorScheme = colorScheme) {
//    WeatherScreen(isDay = isDay)
////    }
//}
@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
//    val isDay = mood == 1
//    val colorScheme = if (isDay) LightColorScheme else DarkColorScheme

//    MaterialTheme(colorScheme = colorScheme) {
    WeatherScreen(viewModel)
//    }
}




